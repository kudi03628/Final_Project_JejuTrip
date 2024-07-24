<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/mypage/member/mypageEditProfile.css"/>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

<style type="text/css">

	table#schedule{
		margin-top: 30px;
	}
	
	table#schedule th, td{
	 	vertical-align: middle;
	}
	
	a{
	    color: #395673;
	    text-decoration: none;
	    cursor: pointer;
	}
	
	a:hover {
	    color: #395673;
	    cursor: pointer;
	    text-decoration: none;
		font-weight: bold;
	}
	
	button.btn_normal{
		background-color: #0071bd;
		border: none;
		color: white;
		width: 70px;
		height: 30px;
		font-size: 12pt;
		padding: 3px 0px;
		margin-right: 10px;
		border-radius: 10%;
	}

</style>

<script type="text/javascript">

$(document).ready(function(){
	
	// ==== 종일체크박스에 체크를 할 것인지 안할 것인지를 결정하는 것 시작 ==== //
	// 시작 시 분
	var str_startdate = $("span#startdate").text();
 // console.log(str_startdate); 
	// 2021-12-01 09:00
	var target = str_startdate.indexOf(":");
	var start_min = str_startdate.substring(target+1);
 // console.log(start_min);
	// 00
	var start_hour = str_startdate.substring(target-2,target);
 //	console.log(start_hour);
	// 09
	
	// 종료 시 분
	var str_enddate = $("span#enddate").text();
//	console.log(str_enddate);
	// 2021-12-01 18:00
	target = str_enddate.indexOf(":");
	var end_min = str_enddate.substring(target+1);
 // console.log(end_min);
    // 00 
	var end_hour = str_enddate.substring(target-2,target);
 //	console.log(end_hour);
	// 18
	
	if(start_hour=='00' && start_min=='00' && end_hour=='23' && end_min=='59' ){
		$("input#allDay").prop("checked",true);
	}
	else{
		$("input#allDay").prop("checked",false);
	}
	// ==== 종일체크박스에 체크를 할 것인지 안할 것인지를 결정하는 것 끝 ==== //
	
}); // end of $(document).ready(function(){})==============================


// ~~~~~~~ Function Declartion ~~~~~~~

// 일정 삭제하기
function delSchedule(scheduleno){

	var bool = confirm("일정을 삭제하시겠습니까?");
	
	if(bool){
		$.ajax({
			url: "<%= ctxPath%>/schedule/deleteSchedule.trip",
			type: "post",
			data: {"scheduleno":scheduleno},
			dataType: "json",
			success:function(json){
				if(json.n==1){
					alert("일정을 삭제하였습니다.");
				}
				else {
					alert("일정을 삭제하지 못했습니다.");
				}
				
				location.href="<%= ctxPath%>/my_schedule.trip";
			},
			error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	        }
		});
	}
	
}// end of function delSchedule(scheduleno){}-------------------------


// 일정 수정하기
function editSchedule(scheduleno){
	var frm = document.goEditFrm;
	frm.scheduleno.value = scheduleno;
	
	frm.action = "<%= ctxPath%>/schedule/editSchedule.trip";
	frm.method = "post";
	frm.submit();
}

</script>

<div class="body">
    <div class="navigation">
        <ul>
            <li class="list">
                <a href="<%= ctxPath%>/requiredLogin_goMypage.trip">
                    <span class="icon"><ion-icon name="bed-outline"></ion-icon></span>
                    <span class="title">예약내역</span>
                </a>
            </li>
            <li class="list">
                <a href="<%= ctxPath%>/editProfile.trip">
                    <span class="icon"><ion-icon name="person-outline"></ion-icon></span>
                    <span class="title">회원정보수정</span>
                </a>
            </li>
            <li class="list active">
                <a href="<%= ctxPath%>/my_schedule.trip">
                    <span class="icon"><ion-icon name="calendar-number-outline"></ion-icon></span>
                    <span class="title">내 일정</span>
                </a>
            </li>
            <li class="list">
                <a href="<%= ctxPath%>/review.trip">
                    <span class="icon"><ion-icon name="clipboard-outline"></ion-icon></span>
                    <span class="title">이용후기</span>
                </a>
            </li>
            <li class="list">
                <a href="<%= ctxPath%>/like.trip">
                    <span class="icon"><ion-icon name="heart-outline"></ion-icon></span>
                    <span class="title">좋아요</span>
                </a>
            </li>
            <br><br><br>
            <li class="list">
                <a href="<%= ctxPath%>/support.trip">
                    <span class="icon"><ion-icon name="help-circle-outline"></ion-icon></span>
                    <span class="title">고객센터</span>
                </a>
            </li>
        </ul>
    </div>
	<div class="container">
		<div style="width: 88%; margin: 50px auto;">
			<h3 style="display: inline-block;">일정 상세보기</h3>&nbsp;&nbsp;<a href="<%= ctxPath%>/my_schedule.trip"><span>◀캘린더로 돌아가기</span></a> 
				
				<table id="schedule" class="table table-bordered">
					<tr>
						<th style="vertical-align: middle;">일자</th>
						<td>
							<span id="startdate">${requestScope.map.STARTDATE}</span>&nbsp;~&nbsp;<span id="enddate">${requestScope.map.ENDDATE}</span>&nbsp;&nbsp;  
							<input type="checkbox" id="allDay" disabled/>&nbsp;종일
						</td>
					</tr>
					<tr>
						<th style="vertical-align: middle;">제목</th>
						<td>${requestScope.map.SUBJECT}</td>
					</tr>
					
					<tr>
						<th style="vertical-align: middle;">캘린더종류</th>
						<td>
						<c:if test="${requestScope.map.FK_LGCATGONO eq '2'}">
							사내 캘린더 - ${requestScope.map.SMCATGONAME}
						</c:if>
						<c:if test="${requestScope.map.FK_LGCATGONO eq '1'}">
							내 캘린더 - ${requestScope.map.SMCATGONAME}
						</c:if></td>
					</tr>
					<tr>
						<th style="vertical-align: middle;">장소</th>
						<td>${requestScope.map.PLACE}</td>
					</tr>
					
					<tr>
						<th style="vertical-align: middle;">공유자</th>
						<td>${requestScope.map.JOINUSER}</td>
					</tr>
					<tr>
						<th style="vertical-align: middle;">내용</th>
						<td><textarea id="content" rows="10" cols="70" style="height: 200px; border: none;" readonly>${requestScope.map.CONTENT}</textarea></td>
					</tr>
					<tr>
						<th style="vertical-align: middle;">작성자</th>
						<td>${requestScope.map.NAME}</td>
					</tr>
				</table>
				
				<input type="hidden" value="${sessionScope.loginuser.userid}" />
				<input type="hidden" value="${requestScope.map.FK_LGCATGONO}" />
				
				<c:set var="v_fk_userid" value="${requestScope.map.FK_USERID}" />
				<c:set var="v_fk_lgcatgono" value="${requestScope.map.FK_LGCATGONO}"/>
				<c:set var="v_loginuser_userid" value="${sessionScope.loginuser.userid}"/>
			
				<div style="float: right; margin-bottom:30px;">
					<button type="button" id="edit" class="btn_normal" onclick="editSchedule('${requestScope.map.SCHEDULENO}')">수정</button>
					<button type="button" class="btn_normal" onclick="delSchedule('${requestScope.map.SCHEDULENO}')">삭제</button>
					<button type="button" id="cancel" class="btn_normal" style="margin-right: 0px; background-color: #990000;" onclick="javascript:location.href='<%= ctxPath%>/my_schedule.trip'">취소</button> 				
				</div>
			</div>
		
		
			<form name="goEditFrm">
				<input type="hidden" name="scheduleno"/>
				<input type="hidden" name="gobackURL_detailSchedule" value="${requestScope.gobackURL_detailSchedule}"/>
			</form>
		
		</div>
</div>