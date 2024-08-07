package com.spring.app.chatting.websockethandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.app.trip.domain.MemberVO;
import com.spring.app.trip.service.Ws_TripService;


// === #226. (웹채팅관련8) === //
//@Component 은 이미 /board/src/main/webapp/WEB-INF/spring/config/websocketContext.xml 파일에서 bean 으로 등록을 해주었으므로 할 필요가 없음.!!
public class WebsocketEchoHandler extends TextWebSocketHandler{
	
	// === 웹소켓서버에 연결한 클라이언트 사용자들을 저장하는 리스트 ===
	private List<WebSocketSession> connectedUsers = new ArrayList<>();
	
	// === 몽고 DB 시작 === // 
	// === #233. 웹채팅관련 16 === // 
	 @Autowired
	 private ChattingMongoOperations chattingMongo;
    
	 @Autowired
	 private Mongo_messageVO dto;
	
	// === 몽고 DB 종료 === // 
	
	 @Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	 private Ws_TripService service;
	 
	 
	public void init() throws Exception{}

	// === 클라이언트가 웹소켓서버에 연결했을때의 작업 처리하기 ===
    /*
       afterConnectionEstablished(WebSocketSession wsession) 메소드는 
              클라이언트가 웹소켓서버에 연결이 되어지면 자동으로 실행되는 메소드로서
       WebSocket 연결이 열리고 사용이 준비될 때 호출되어지는(실행되어지는) 메소드이다.
    */
	
	@Override
	public void afterConnectionEstablished(WebSocketSession wsession) throws Exception { 
		// >>> 파라미터 WebSocketSession wsession 은 웹소켓서버에 접속한 클라이언트 사용자임. <<< 
		
		// 웹소켓서버에 접속한 클라이언트의 IP Address 얻어오기
		/*
         	STS 메뉴의 
         	Run --> Run Configuration 
             	--> Arguments 탭
             	--> VM arguments 속에 맨 뒤에
             	--> 한칸 띄우고 -Djava.net.preferIPv4Stack=true 
                              을 추가한다.  
		 */
		
		// System.out.println("웹 채팅 확인용 : " + wsession.getId() + "님이 접속했습니다.");
		// System.out.println("====> 웹채팅확인용 : " + "연결 컴퓨터명 : " + wsession.getRemoteAddress().getHostName());
		// System.out.println("====> 웹채팅확인용 : " + "연결 컴퓨터명 : " + wsession.getRemoteAddress().getAddress().getHostName());
		// System.out.println("====> 웹채팅확인용 : " + "연결 IP : " + wsession.getRemoteAddress().getAddress().getHostAddress());
		connectedUsers.add(wsession);
		
		///// ===== 웹소켓 서버에 접속시 접속자명단을 알려주기 위한 것 시작 ===== /////
        // Spring에서 WebSocket 사용시 먼저 HttpSession에 저장된 값들을 읽어와서 사용하기
		/*
			먼저 /webapp/WEB-INF/spring/config/websocketContext.xml 파일에서
          	websocket:handlers 태그안에 websocket:handshake-interceptors에
            HttpSessionHandshakeInterceptor를 추가하면 WebsocketEchoHandler 클래스를 사용하기 전에 
			먼저 HttpSession에 저장되어진 값들을 읽어 들여 WebsocketEchoHandler 클래스에서 사용할 수 있도록 처리해준다. 
		 */
		String chatting_key = "";
		String connectingUserName = "「";  //「 은  자음 ㄴ 을 하면 나온다.
		
		for(WebSocketSession webSocketSession:connectedUsers) {
			Map<String,Object> map = webSocketSession.getAttributes();
			/*
            	webSocketSession.getAttributes(); 은 
            	HttpSession에 setAttribute("키",오브젝트); 되어 저장되어진 값들을 읽어오는 것으로써,
				리턴값은  "키",오브젝트로 이루어진 Map<String, Object> 으로 받아온다.
			 */
			chatting_key = String.valueOf(map.get("chatting_key"));
			MemberVO loginuser = (MemberVO)map.get("chattinguser");
			// "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
			connectingUserName += loginuser.getUser_name() + " ";
		}
		connectingUserName += "」";
		
		for(WebSocketSession webSocketSession:connectedUsers) {
			webSocketSession.sendMessage(new TextMessage(connectingUserName));
		}
		///// ===== 웹소켓 서버에 접속시 접속자명단을 알려주기 위한 것 종료 ===== /////
		
		// ================== 몽고DB 시작 ================== //
        List<Mongo_messageVO> list = chattingMongo.listChatting(); // 몽고DB에 저장되어진 채팅내용을 읽어온다.
         
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy년 MM월 dd일 E요일", Locale.KOREAN);
        
        if(list != null && list.size() > 0) { // 이전에 나누었던 대화내용이 있다라면 
          
        	for(int i=0; i<list.size(); i++) {
        		if( list.get(i).getChatting_key() != null && list.get(i).getChatting_key().equalsIgnoreCase(chatting_key)) {
        			
        			String str_created = sdfrmt.format(list.get(i).getCreated());  // 대화내용을 나누었던 날짜를 읽어온다. 2024년 05월 09일 목요일
                    
             	   //         System.out.println(list.get(i).getUserid() + "\n"
             	   //                          + list.get(i).getName() + "\n"
             	   //                          + list.get(i).getCurrentTime() + "\n"
             	   //                          + list.get(i).getMessage() + "\n"
             	   //                          + list.get(i).getCreated() + "\n"
             	   //                          + str_created + "\n"
             	   //                          + list.get(i).getCurrentTime() + "\n" );
                   
             	   // }// end of for-----------------------------------
                
             	   // System.out.println("=================================\n");
     		
             	   boolean is_newDay = true; // 대화내용의 날짜가 같은 날짜인지 새로운 날짜인지 알기위한 용도임.
                   
             	   if( i > 0 && str_created.equals( sdfrmt.format(list.get(i-1).getCreated()) )  ) {  // 다음번 내용물에 있는 대화를 했던 날짜가 이전 내용물에 있는 대화를 했던 날짜와 같다라면  
             		   is_newDay = false; // 이 대화내용은 새로운 날짜의 대화가 아님을 표시한다.        
             	   }
                   
             	   if(is_newDay) {     
             		   wsession.sendMessage(
         				   new TextMessage("<div style='text-align: center; background-color: #ccc;'>" + str_created + "</div>")  
     				   ); // 대화를 나누었던 날짜를 배경색을 회색으로 하여 보여주도록 한다.
             	   }
             	   Map<String, Object> map = wsession.getAttributes();
                    /*
                       	wsession.getAttributes(); 은 
                       	HttpSession에 setAttribute("키",오브젝트); 되어 저장되어진 값들을 읽어오는 것으로써,
                   		리턴값은  "키",오브젝트로 이루어진 Map<String, Object> 으로 받아온다.
                    */ 
                    
             	   MemberVO loginuser = (MemberVO)map.get("chattinguser");  
                    // "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
                            
             	   if(loginuser.getUserid().equals(list.get(i).getUserid())) { // 본인이 작성한 채팅메시지일 경우라면.. 작성자명 없이 노랑배경색에 오른쪽 정렬로 보이게 한다.
             		   if("all".equals(list.get(i).getType())) { // 귀속말이 아닌 경우
                 		  	
             			   if(list.get(i).getMessage().contains(".jpg") || list.get(i).getMessage().contains(".png")) {
             				  wsession.sendMessage(
                   		  			new TextMessage("<div style='background-color: #ffff80; display: inline-block; max-width: 80%; float: right; padding: 7px; border-radius: 8px; word-break: break-all;'><img style='width:100%;' src='"+map.get("ctxPath")+"/resources/images/chatting/"+list.get(i).getMessage()+"'>" + "</div> <div style='display: inline-block; float: right; padding: 20px 5px 0 0; font-size: 7pt;'>"+list.get(i).getCurrentTime()+"</div> <div style='clear: both;'>&nbsp;</div>")  
             				  ); 
             			   }
             			   else {
             				  wsession.sendMessage(
                     		  			new TextMessage("<div style='background-color: #ffff80; display: inline-block; max-width: 60%; float: right; padding: 7px; border-radius: 8px; word-break: break-all;'>" + list.get(i).getMessage() + "</div> <div style='display: inline-block; float: right; padding: 20px 5px 0 0; font-size: 7pt;'>"+list.get(i).getCurrentTime()+"</div> <div style='clear: both;'>&nbsp;</div>")  
               				  ); 
             			   }
             			   
             		   }
             		   else { // 귀속말인 경우. 글자색을 빨강색으로 함.
                 		  	wsession.sendMessage(
             		  			new TextMessage("<div style='background-color: #ffff80; display: inline-block; max-width: 60%; float: right; padding: 7px; border-radius: 8px; word-break: break-all; color: red;'>" + list.get(i).getMessage() + "</div> <div style='display: inline-block; float: right; padding: 20px 5px 0 0; font-size: 7pt;'>"+list.get(i).getCurrentTime()+"</div> <div style='clear: both;'>&nbsp;</div>")  
                     		);
             		   }
             	   }
                   
             	   else { // 다른 사람이 작성한 채팅메시지일 경우라면.. 작성자명이 나오고 흰배경색으로 보이게 한다.
             		   if("all".equals(list.get(i).getType())) { // 귀속말이 아닌 경우
                 		  if(list.get(i).getMessage().contains(".jpg") || list.get(i).getMessage().contains(".png")) {
                 			 wsession.sendMessage(
                    				  new TextMessage("<img style='width:50px;' src='/JejuDream/resources/images/chatting_icon.png'/>[<span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +list.get(i).getName()+ "</span>]<br><div style='background-color: white; display: inline-block; max-width: 80%; padding: 7px; border-radius: 8px; word-break: break-all;'><img style='width:100%;' src='"+map.get("ctxPath")+"/resources/images/chatting/"+list.get(i).getMessage()+"'>"+"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+list.get(i).getCurrentTime()+"</div> <div>&nbsp;</div>" ) 
                				  );
                 		  }
                 		  else {
                 			 wsession.sendMessage(
                    				  new TextMessage("<img style='width:50px;' src='/JejuDream/resources/images/chatting_icon.png'/>[<span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +list.get(i).getName()+ "</span>]<br><div style='background-color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 8px; word-break: break-all;'>"+ list.get(i).getMessage() +"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+list.get(i).getCurrentTime()+"</div> <div>&nbsp;</div>" ) 
                		     );
                 		  }
             		   }
             		   else { // 귀속말인 경우. 글자색을 빨강색으로 함.
                 		  	wsession.sendMessage(
             		  			new TextMessage("<img style='width:50px;' src='/JejuDream/resources/images/chatting_icon.png'/>[<span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +list.get(i).getName()+ "</span>]<br><div style='background-color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 8px; word-break: break-all; color: red;'>"+ list.get(i).getMessage() +"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+list.get(i).getCurrentTime()+"</div> <div>&nbsp;</div>" ) 
         		  			);
             		   }
             	   }
        		}
              
            }// end of for-----------------------------------     
           
         }// end of if if(list != null && list.size() > 0)-------------------------------------
              
	}
	// === 클라이언트가 웹소켓 서버로 메시지를 보냈을때의 Send 이벤트를 처리하기 ===
    /*
	       	handleTextMessage(WebSocketSession wsession, TextMessage message) 메소드는 
			클라이언트가 웹소켓서버로 메시지를 전송했을 때 자동으로 호출되는(실행되는) 메소드이다.
			첫번째 파라미터  WebSocketSession 은  메시지를 보낸 클라이언트임.
			두번째 파라미터  TextMessage 은  메시지의 내용임.
     */
	@Override
	public void handleTextMessage(WebSocketSession wsession, TextMessage message) throws Exception { 
      
		// >>> 파라미터 WebSocketSession wsession은  웹소켓서버에 접속한 클라이언트임. <<<
		// >>> 파라미터 TextMessage message 은  클라이언트 사용자가 웹소켓서버로 보낸 웹소켓 메시지임. <<<
       
		// Spring에서 WebSocket 사용시 먼저 HttpSession에 저장된 값들을 읽어와서 사용하기
		/*
          	먼저 /webapp/WEB-INF/spring/config/websocketContext.xml 파일에서
          	websocket:handlers 태그안에 websocket:handshake-interceptors에
            HttpSessionHandshakeInterceptor를 추가해주면 
            WebSocketHandler 클래스를 사용하기 전에, 
         	먼저 HttpSession에 저장되어진 값들을 읽어 들여, WebSocketHandler 클래스에서 사용할 수 있도록 처리해준다. 
		 */
      
      Map<String, Object> map = wsession.getAttributes(); 
      MemberVO loginuser = (MemberVO) map.get("chattinguser");
      String status = String.valueOf(map.get("status"));
      // "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
      
      // System.out.println("===> 웹채팅확인용 : 로그인ID : " + loginuser.getUserid());
      // ===> 웹채팅확인용 : 로그인ID : seoyh 
      
      MessageVO messageVO = MessageVO.convertMessage(message.getPayload()); 
      /* 
    		파라미터 message 는  클라이언트 사용자가 웹소켓서버로 보낸 웹소켓 메시지임
         	message.getPayload() 은  클라이언트 사용자가 보낸 웹소켓 메시지를 String 타입으로 바꾸어주는 것이다.
        	/Board/src/main/webapp/WEB-INF/views/tiles1/chatting/multichat.jsp 파일에서 
        	클라이언트가 보내준 메시지는 JSON 형태를 뛴 문자열(String) 이므로 이 문자열을 Gson을 사용하여 MessageVO 형태의 객체로 변환시켜서 가져온다.
       */
      
      // System.out.println("~~~ 확인용 messageVO.getMessage() => " + messageVO.getMessage() );  
      // ~~ 확인용 messageVO.getMessage() => 채팅방에 <span style='color: red;'>입장</span> 했습니다.
      // ~~ 확인용 messageVO.getMessage() => 나는 이순신 입니다. ㅎㅎㅎ 
      
      // System.out.println("~~~ 확인용 messageVO.getType() => " + messageVO.getType() );
      // ~~~ 확인용 messageVO.getType() => all 
      
      // System.out.println("~~~ 확인용 messageVO.getTo() => " + messageVO.getTo() );
      // ~~~ 확인용 messageVO.getTo() => all
      
      
      Date now = new Date(); // 현재시각 
      String currentTime = String.format("%tp %tl:%tM",now,now,now); 
      // %tp              오전, 오후를 출력
      // %tl              시간을 1~12 으로 출력
      // %tM              분을 00~59 으로 출력
      
      for(WebSocketSession webSocketSession : connectedUsers) { 
           
    	  if("all".equals(messageVO.getType())) {
              // 채팅할 대상이 "전체" 일 경우
              // 메시지를 자기자신을 뺀 나머지 모든 사용들에게 메시지를 보냄.
              
              if( !wsession.getId().equals(webSocketSession.getId()) ) { 
            	  // wsession 은 메시지를 보낸 클라이언트임.
            	  // webSocketSession 은 웹소켓서버에 연결된 모든 클라이언트중 하나임.
            	  // wsession.getId() 와  webSocketSession.getId() 는 자동증가되는 고유한 값으로 나옴 
                 
            	  if(messageVO.getMessage().contains(".jpg") || messageVO.getMessage().contains(".png")) {
            		  webSocketSession.sendMessage(   
                			  new TextMessage("<span style='display:none'>"+wsession.getId()+"</span>&nbsp;<img style='width:50px;' src='/JejuDream/resources/images/chatting_icon.png'/>[<span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +loginuser.getUser_name()+ "</span>]<br><div style='background-color: white; display: inline-block; max-width: 80%; padding: 7px; border-radius: 8px; word-break: break-all;'><img style='width:100%;' src='"+map.get("ctxPath")+"/resources/images/chatting/"+messageVO.getMessage()+"'>" +"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+currentTime+"</div> <div>&nbsp;</div>" )); 
     			   }
            	  else {
            		  webSocketSession.sendMessage(   
                			  new TextMessage("<span style='display:none'>"+wsession.getId()+"</span>&nbsp;<img style='width:50px;' src='/JejuDream/resources/images/chatting_icon.png'/>[<span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +loginuser.getUser_name()+ "</span>]<br><div style='background-color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 8px; word-break: break-all;'>"+ messageVO.getMessage() +"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+currentTime+"</div> <div>&nbsp;</div>" )); 
                                
            	  }
            	                                                                                                                                                                                                                                                                                                       /* word-break: break-all; 은 공백없이 영어로만 되어질 경우 해당구역을 빠져나가므로 이것을 막기위해서 사용한다. */
				}
              
    	  }
           
    	  else { // 채팅할 대상이 "전체"가 아닌 특정대상(귀속말대상웹소켓.getId()임) 일 경우  
    		  
              String ws_id = webSocketSession.getId();
                          // webSocketSession 은 웹소켓서버에 연결한 모든 클라이언트중 하나이며, 그 클라이언트의 웹소켓의 고유한 id 값을 알아오는 것임. 
              
              if(messageVO.getTo().equals(ws_id)) { 
            	  // messageVO.getTo() 는 클라이언트가 보내온 귓속말대상웹소켓.getId() 임. 
            	  webSocketSession.sendMessage(
    			  	new TextMessage("<span style='display:none'>"+wsession.getId()+"</span>&nbsp;[<span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +loginuser.getUser_name()+ "</span>]<br><div style='background-color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 8px; word-break: break-all; color: red;'>"+ messageVO.getMessage() +"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+currentTime+"</div> <div>&nbsp;</div>" )); 
                                                                                                                                                                                                                                                                                                                             /* word-break: break-all; 은 공백없이 영어로만 되어질 경우 해당구역을 빠져나가므로 이것을 막기위해서 사용한다. */ 
            	  	break; // 지금의 특정대상(지금은 귓속말대상 웹소켓id)은 1개이므로 
                           // 특정대상(지금은 귓속말대상 웹소켓id 임)에게만 메시지를 보내고  break;를 한다.
              }
           }
           
        }// end of for-------------------------------------
      
      	// ======================== 몽고DB 시작 ======================== //
      	// === 상대방에게 대화한 내용을 위에서 보여준 후, 몽고DB에 저장하도록 한다. === // 
      	String _id = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
      	_id += System.nanoTime();
      	dto.set_id(_id);
      	dto.setMessage(messageVO.getMessage());
      	dto.setTo(messageVO.getTo());
      	dto.setType(messageVO.getType());
      	
      	dto.setUserid(loginuser.getUserid());
      	dto.setName(loginuser.getUser_name());
      	dto.setCurrentTime(currentTime);
      	dto.setCreated(new Date());
        dto.setChatting_key(String.valueOf(map.get("chatting_key")));
      	chattingMongo.insertMessage(dto);
      	// ================== 몽고DB 끝 ============================== //
      	
      	String[] chatting_key_arr = String.valueOf(map.get("chatting_key")).split("_");
      	
      	if(status.equals("1")) {
      		Map<String,String> chatMap = new HashMap<>();
      		String to_id = chatting_key_arr[2];
      		String from_id = chatting_key_arr[1];
      		String fk_reservation_code = chatting_key_arr[0];
      		chatMap.put("to_id", to_id);
      		chatMap.put("from_id", from_id);
      		chatMap.put("fk_reservation_code", fk_reservation_code);
      		service.insert_send_chatting(chatMap);// 채팅을 보냈다는 기록을 남겨준다. 추후에 이 기록을 토대로 페이지에 신규 채팅이 있는지 없는지 표시를 해주기위함이다.
      	}
      	if(status.equals("2")) {
      		Map<String,String> chatMap = new HashMap<>();
      		String from_id = chatting_key_arr[2];
      		String to_id = chatting_key_arr[1];
      		String fk_reservation_code = chatting_key_arr[0];
      		chatMap.put("to_id", to_id);
      		chatMap.put("from_id", from_id);
      		chatMap.put("fk_reservation_code", fk_reservation_code);
      		service.insert_send_chatting(chatMap);// 채팅을 보냈다는 기록을 남겨준다. 추후에 이 기록을 토대로 페이지에 신규 채팅이 있는지 없는지 표시를 해주기위함이다.
      	}
      	if(status.equals("3")) {
      		Map<String,String> chatMap = new HashMap<>();
      		String to_id = chatting_key_arr[1];
      		String from_id = chatting_key_arr[0];
      		chatMap.put("to_id", to_id);
      		chatMap.put("from_id", from_id);
      		chatMap.put("fk_reservation_code", "0");
      		service.insert_send_chatting(chatMap);// 채팅을 보냈다는 기록을 남겨준다. 추후에 이 기록을 토대로 페이지에 신규 채팅이 있는지 없는지 표시를 해주기위함이다.
      	}
      	if(status.equals("4")) {
      		Map<String,String> chatMap = new HashMap<>();
      		String to_id = chatting_key_arr[0];
      		String from_id = chatting_key_arr[1];
      		chatMap.put("to_id", to_id);
      		chatMap.put("from_id", from_id);
      		chatMap.put("fk_reservation_code", "0");
      		service.insert_send_chatting(chatMap);// 채팅을 보냈다는 기록을 남겨준다. 추후에 이 기록을 토대로 페이지에 신규 채팅이 있는지 없는지 표시를 해주기위함이다.
      	}
	}
	
	// === 클라이언트가 웹소켓서버와의 연결을 끊을때 작업 처리하기 ===
	/*
      	afterConnectionClosed(WebSocketSession session, CloseStatus status) 메소드는 클라이언트가 연결을 끊었을 때 
     	즉, WebSocket 연결이 닫혔을 때(채팅페이지가 닫히거나 채팅페이지에서 다른 페이지로 이동되는 경우) 자동으로 호출되어지는(실행되어지는) 메소드이다.
	 */
	@Override
    public void afterConnectionClosed(WebSocketSession wsession, CloseStatus status) throws Exception {
        // 파라미터 WebSocketSession wsession 은 연결을 끊은 웹소켓 클라이언트임.
        // 파라미터 CloseStatus 은 웹소켓 클라이언트의 연결 상태.
       
		Map<String, Object> map = wsession.getAttributes(); 
		MemberVO loginuser = (MemberVO)map.get("chattinguser");
       
        for (WebSocketSession webSocketSession : connectedUsers) {
           
        	// 퇴장했다라는 메시지를 자기자신을 뺀 나머지 모든 사용자들에게 메시지를 보내도록 한다.
        	if (!wsession.getId().equals(webSocketSession.getId())) { 
                webSocketSession.sendMessage(
            		new TextMessage("[<span style='font-weight:bold;'>" +loginuser.getUser_name()+ "</span>]님이 <span style='color: red;'>퇴장</span>했습니다.")
                ); 
            }
        }// end of for------------------------------------------
       
        //  System.out.println("====> 웹채팅확인용 : 웹세션ID " + wsession.getId() + "이 퇴장했습니다."); 
        
        connectedUsers.remove(wsession);
        // 웹소켓 서버에 연결되어진 클라이언트 목록에서 연결은 끊은 클라이언트는 삭제시킨다.  
   
        ///// ===== 접속을 끊을시 접속자명단을 알려주기 위한 것 시작 ===== /////
        String connectingUserName = "「";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            Map<String, Object> map2 = webSocketSession.getAttributes();
            MemberVO loginuser2 = (MemberVO)map2.get("chattinguser");  
            // "loginuser" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
   
            connectingUserName += loginuser2.getUser_name()+" "; 
        }// end of for------------------------------------------
        
        connectingUserName += "」";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            webSocketSession.sendMessage(new TextMessage(connectingUserName));
        }// end of for------------------------------------------
        ///// ===== 접속을 끊을시 접속자명단을 알려주기 위한 것 끝 ===== /////
        if(Integer.parseInt(String.valueOf(map.get("status"))) < 3) {
        	String to_id = loginuser.getUserid();
            String[] chatting_key_arr = String.valueOf(map.get("chatting_key")).split("_");
            String fk_reservation_code = chatting_key_arr[0];
            Map<String,String> paraMap = new HashMap<>();
            paraMap.put("to_id",to_id);
            paraMap.put("fk_reservation_code", fk_reservation_code);
            service.update_chattinglog_after_chatting(paraMap);// 채팅방에서 나가게되면 채팅 기록방에서 나가기 직전까지의 읽음 컬럼을 읽음처리로 바꿔준다.
        }
        else {
        	String to_id = loginuser.getUserid();
            Map<String,String> paraMap = new HashMap<>();
            paraMap.put("to_id",to_id);
            paraMap.put("fk_reservation_code", "0");
            service.update_chattinglog_after_chatting(paraMap);// 채팅방에서 나가게되면 채팅 기록방에서 나가기 직전까지의 읽음 컬럼을 읽음처리로 바꿔준다.
        }
        
    }
}
