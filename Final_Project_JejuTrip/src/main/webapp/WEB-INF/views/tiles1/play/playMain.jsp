<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String ctxPath = request.getContextPath();
    //    /JejuDream
%>



<style type="text/css">


.single-post {
    margin-bottom: 20px;
    border: 1px solid #ebebeb;
    border-radius: 5px;
    overflow: hidden;
    transition: all 0.3s ease;
}

.single-post:hover {
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}


.title a {
    margin: 10px 0;
    color: black;
    text-decoration: none;
    transition-duration: 300ms;
    text-transform: capitalize;

}

.title a:hover {
    color: #ffdccc;
    text-decoration: none;
}

/*-----------------------------------------------------------------------------------------  */

*{
  margin: 0;
  padding: 0;
  -webkit-box-sizing: border-box;
          box-sizing: border-box;
}

.wrapper{
  width: 100%;
  margin: 0 auto;
  max-width: 150rem;
}

.cols{
  display: flex;
  justify-content: center;
  -ms-flex-wrap: wrap;
      flex-wrap: wrap;
  -webkit-box-pack: center;
}

.col{
  width: calc(25% - 2rem);
  margin: 1rem;
  cursor: pointer;
}

.container_card{

  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-perspective: 1000px;
          perspective: 1000px;
  margin-bottom: 40px;
}

.front,
.back{
  background-size: cover;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.25);
  border-radius: 10px;
  background-position: center;
  -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -webkit-backface-visibility: hidden;
          backface-visibility: hidden;
  text-align: center;
  min-height: 400px;
  height: auto;
  border-radius: 10px;
  color: #fff;
  font-size: 1.5rem;

}

.back{
 	background: #d1d1e0;
}

.front:after{
  position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
    content: '';
    display: block;
    opacity: .1;
    background-color: #000;
    -webkit-backface-visibility: hidden;
            backface-visibility: hidden;
    border-radius: 10px;
}
.container_card:hover .front,
.container_card:hover .back{
    -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
}

.back{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
}

.inner_front{
    -webkit-transform: translateY(-50%) translateZ(60px) scale(0.94);
            transform: translateY(-50%) translateZ(60px) scale(0.94);
    top: 70%;
    position: absolute;
    left: 90px;
    width: 100%;
    padding: 2rem;
    -webkit-box-sizing: border-box;
            box-sizing: border-box;
    outline: 1px solid transparent;
    -webkit-perspective: inherit;
            perspective: inherit;
    z-index: 2;
    font-weight: bold;
    color: black;
}
.inner_front:before {
  content: '';
  position: absolute;
  top: 50%; 							/* 요소 중앙에 배치 */
  left: 50%;							/* 요소 중앙에 배치 */
  transform: translate(-50%, -50%);		/* 중앙 정렬 보정 */
  width: 240px; 						/* 동그라미의 너비 */
  height: 240px; 						/* 동그라미의 높이 */
  background-color: rgba(255, 255, 255, 0.7); /* 동그라미 색상 및 불투명도 */
  border-radius: 50%; 					/* 동그라미 모양으로 만들기 */
  z-index: -1; 							/* 텍스트 뒤로 배치 */
  }
  
 
.inner_back{
    border: 0px red solid;
    position: absolute;
    width: 100%;
    height: 400px;
    padding: 1rem;
    text-align: left;
    overflow: auto;
    color: black;
    
}

 .inner_back::-webkit-scrollbar {
    width: 10px;
  }
  .inner_back::-webkit-scrollbar-thumb {
    background-color: #ff8000;
    border-radius: 10px;
    background-clip: padding-box;
    border: 2px solid transparent;
  }
  .inner_back::-webkit-scrollbar-track {
    background-color: grey;
    border-radius: 10px;
    box-shadow: inset 0px 0px 5px white;
  }

.container_card .back{
    -webkit-transform: rotateY(180deg);
            transform: rotateY(180deg);
            cursor: pointer;
   
}

.container_card .front{
    -webkit-transform: rotateY(0deg);
            transform: rotateY(0deg);
  
}

.container_card:hover .back{
  -webkit-transform: rotateY(0deg);
          transform: rotateY(0deg);
          cursor: pointer;
  
}

.container_card:hover .front{
  -webkit-transform: rotateY(-180deg);
          transform: rotateY(-180deg);
          cursor: pointer;
 
}


.front .inner_front p{
  margin-top: -3%;
  font-size: 18px;
  margin-bottom: 2rem;
  position: relative;
}

.front .inner_front p:after{
  content: '';
  width: 4rem;
  height: 2px;
  position: absolute;
  background: #ff8000;
  display: block;
  left: 0;
  right: 0;
  margin: 0 auto;
  bottom: -.75rem;
  
}

.list-group-item  {cursor: pointer; }
   .moveColor {color: #660029; font-weight: bold; background-color: #ffffe6;}

.inner_back div span{
font-size: 17px;

}
/*-----------------------------------------------------------------------------------------  */

img.icon {
  width: 5%;
  margin-bottom: 3%;
}



</style>


<script type="text/javascript">


$(document).ready(function() {
	
	let currentShowPageNo = 1; // currentShowPageNo 초기값
    
	contentPlay(currentShowPageNo); // 페이지 불러오는 함수
    updateCategoryCount(); // 카테고리별 수량 알아오는 함수
    
    $(".list-group-item").hover(function(e) {
        $(e.target).addClass("moveColor");
    }, function(e) {
        $(e.target).removeClass("moveColor");
    });

    
    // 카드 클릭 시 play_code 전달 
    $(document).on('click', '.container_card', function() {
        const playCode = $(this).find('input[name="play_code"]').val(); // 클릭된 카드의 play_code 값 가져오기
        goAddSchedule(playCode);
    });
    
   
     $(document).on('click', '.search', function() {
    	 searchWord = $(this).parent().find('input[name="searchWord"]').val();
         //console.log("searchWord",searchWord);
         const frm = document.totalPlayFrm;
         frm.searchWord.value = searchWord;
         contentPlay(1);
    }); 

     
     $("input:text[name='searchWord']").bind("keydown", function(e){
         if(e.keyCode == 13){ // 엔터
            
        	 searchWord = $(this).parent().find('input[name="searchWord"]').val();
             //console.log("searchWord",searchWord);
             const frm = document.totalPlayFrm;
             frm.searchWord.value = searchWord;
             
             contentPlay(1);
         }
     });

    // ================================ 지역구분 체크박스 ================================ //
    
    const localAllCheckbox = $('input#all_local');
    const localCheckboxes = $('input[name="local_status"]').not('#all_local');

    // 전체 체크박스를 체크하면 나머지 체크박스를 해제
    localAllCheckbox.change(function() {
        if(localAllCheckbox.is(':checked')) {
            localCheckboxes.prop('checked', false);
        }
    });

    // 나머지 체크박스를 체크하면 전체 체크박스의 상태를 업데이트
    localCheckboxes.change(function () {
        const allChecked = localCheckboxes.length === localCheckboxes.filter(':checked').length;
        localAllCheckbox.prop('checked', allChecked);
        if (allChecked) {
            localCheckboxes.prop('checked', false);
        }
    });
    
    //지역구분 체크박스를 클릭할때 이벤트
    $('input[name="local_status"]').on('change', function() {
    	const arr_local = [];
        $("input:checkbox[name='local_status']:checked").each(function(index, elmt) {
            arr_local.push($(elmt).val());
        });
        const str_local = arr_local.join();
        //console.log("str_local", str_local);

        const frm = document.totalPlayFrm; // frm 으로 제일 아래에 있는 form 가져오고
        frm.str_local.value = str_local;   // 그 form 안에 있는 str_local 에 str_local값을 넣는다
        
        contentPlay(1);
       
    });
    // ================================ 지역구분 체크박스 ================================ //
    
    
    
  //================================ 카테고리 클릭 이벤트 시작 ================================//
    $('.list-group-item').on('click', function() {
        category = $(this).find('input').val();
        const frm = document.totalPlayFrm;
        frm.category.value = category;
        contentPlay(1);
        
    });
    //================================ 카테고리 클릭 이벤트용 끝 ================================//
    
    

    
});//end of $(document).ready(function()-----------------------------



//-----------------------디테일 겸 예약할수있는 페이지 호출 함수--------------------------------//

function goAddSchedule(playCode) {
	
	location.href= `<%= ctxPath %>/goAddSchedule.trip?play_code=\${playCode}`;
	
}
//----------------------------------------------------------------------------//


function contentPlay(currentShowPageNo) {
	goTop();
	$("input:hidden[name='currentShowPageNo']").val(currentShowPageNo);
    const formData = $("form[name='totalPlayFrm']").serialize(); //totalPlayFrm 폼에 담았던  데이터들을 전체 ~~ 
    
    $.ajax({
        url: "<%= ctxPath %>/playMainJSON.trip",
        data: formData,
        type: "get",
        dataType: "json",
        success: function (json) {
            let v_html = "";

            if (json.length > 0) {
                $.each(json, function (index, item) {
                    v_html += "    <div class='col-md-6' ontouchstart='this.classList.toggle(\"hover\");'>";
                    v_html += "      <div class='container_card'>";
                    v_html += "        <div class='front' style='background-image: url(<%= ctxPath %>/resources/images/play/" + item.play_main_img + ")'>";
                    v_html += "          <div class='inner_front'>";
                    v_html += "             <img class='icon' src='<%= ctxPath %>/resources/images/foodstore/icon/LikeUp.png'>"
                    v_html += "             <p class='count'>"+item.likeCount+"</p>"
                    v_html += "             <p style='font-size: 20px;font-weight: bold;'>" + item.play_name + "</p>";
                    v_html += "             <span style=' color:#786b94;font-size: 15px;'>" + item.play_category + "</span>";
                    v_html += "         	<input type='hidden' name='play_code' value='" + item.play_code + "'/>"; 
                    v_html += "          </div>";
                    v_html += "        </div>";
                    v_html += "        <div class='back'>";
                    v_html += "          <div class='inner_back'>";
                    v_html += "            <div>";
                    v_html += "              <span><img src='<%= ctxPath %>/resources/images/play/rogo.png' style='width: 30px;'> 행사정보</span><br>";
                    v_html += "              <span class='inner_back_content'>" + item.play_content + "</span>";
                    v_html += "            </div>";
                    v_html += "            <br>";
                    v_html += "            <div>";
                    v_html += "              <span><img src='<%= ctxPath %>/resources/images/play/rogo.png' style='width: 30px;'> 운영시간 </span><br>";
                    v_html += "              <span class='open_time'>" + item.play_businesshours + "</span>";
                    v_html += "            </div>";
                    v_html += "            <br>";
                    v_html += "            <div>";
                    v_html += "              <span><img src='<%= ctxPath %>/resources/images/play/rogo.png' style='width: 30px;'> 오시는길 </span><br>";
                    v_html += "              <span class='adress'>" + item.play_address + "</span>";
                    v_html += "            </div>";
                    v_html += "          </div>";
                    v_html += "        </div>";
                    v_html += "      </div>";
                    v_html += "  </div>";
                });
	            const totalPage = Math.ceil(json[0].totalCount / json[0].sizePerPage);
	            PageBar(currentShowPageNo, totalPage);
	            
            }
            else {
            	const totalPage = 0;
                v_html += "현재 카테고리 준비중 입니다...";
                PageBar(currentShowPageNo, totalPage);
            }
            $("div#categoryList").html(v_html);
            
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
    });
}//end of function contentPlay(category)--------------------------------

//----------------------------------------------------------------------------//


// 페이지바 함수
function PageBar(currentShowPageNo,totalPage){
	   
    const blockSize = 10;
	let loop = 1;
	let pageNo = Math.floor((currentShowPageNo - 1)/blockSize) * blockSize + 1;
	
	let pageBar_HTML = "<ul style='list-style:none'>";
	
	// [맨처음] [이전] 만들기
	if(pageNo != 1) {
		pageBar_HTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:contentPlay(1)'>[맨처음]</a></li>";
		pageBar_HTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:contentPlay("+(pageNo-1)+")'>[이전]</a></li>";
	}
	
	while(!(loop>blockSize || pageNo > totalPage)) {
		if(pageNo == currentShowPageNo) {
			pageBar_HTML += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</a></li>";
		}
		else {
			pageBar_HTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:contentPlay("+pageNo+")'>"+pageNo+"</a></li>";
		}
		loop++;
		pageNo++;
	}//end of while
	
	// [다음] [마지막] 만들기
	if(pageNo <= totalPage) {
		pageBar_HTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:contentPlay("+(pageNo+1)+")'>[다음]</a></li>";
		pageBar_HTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:contentPlay("+totalPage+")'>[마지막]</a></li>";
	}
	
	
	pageBar_HTML += "</ul>";
	
	// 156.댓글 페이지바 출력하기
	$("div#pageBar").html(pageBar_HTML);
	
}

function updateCategoryCount() {
    $.ajax({
        url: "<%= ctxPath %>/getCategoryCount.trip",
        dataType: "json",
        success: function(data) {
            $("span[data-category='total']").html(data.total);
            $("span[data-category='tourism']").html(data.tourism);
            $("span[data-category='showing']").html(data.showing);
            $("span[data-category='experience']").html(data.experience);
        },
        error: function(request, status, error) {
            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
    });
}


function goTop() {
    $(window).scrollTop(0);
}

/////////////////////////////////////////////////////////////////////////





</script>


</head>

<body>
	
    
    <div class="container">
        <div class="row">
            <div class="col-md-2">
			    <ul class="list-group" style="border-radius: 20px;">
			        <li class="list-group-item d-flex justify-content-between align-items-center" style="margin-top: 240px;">
			            <input type="hidden" name="total" value=""/>
			            <label for="total" style="font-weight: bold;">전체</label>
			            <span class="badge badge-pill" style="background:#ff8000; color:#fff;" data-category="total">0</span>
			        </li>
			        <li class="list-group-item d-flex justify-content-between align-items-center">
			            <input type="hidden" name="tourism" value="관광지"/>
			            <label for="tourism" style="font-weight: bold;">관광지</label>
			            <span class="badge badge-pill" style="background:#ff8000; color:#fff;" data-category="tourism">0</span>
			        </li>
			        <li class="list-group-item d-flex justify-content-between align-items-center">
			            <input type="hidden" name="showing" value="전시회"/>
			            <label for="showing" style="font-weight: bold;">전시회</label>
			            <span class="badge badge-pill" style="background:#ff8000; color:#fff;" data-category="showing">0</span>
			        </li>
			        <li class="list-group-item d-flex justify-content-between align-items-center">
			            <input type="hidden" name="experience" value="체험"/>
			            <label for="experience" style="font-weight: bold;">체험</label>
			            <span class="badge badge-pill" style="background:#ff8000; color:#fff;" data-category="experience">0</span>
			        </li>
			    </ul>
			</div>
            
            <div class="col-md-10 py-3">
                <div class="row py-8">
                    <div id="tabArea" class="tabArea1 text-center" style="display: flex; margin:3% auto 6%; ">
                        <div class="areaMap" style="display: flex;">
                            <div class="areamap mx-2" style="width: 25%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_total.png" />
                                <div>
                                    <input name="local_status" id="all_local" type="checkbox" class="are_map" value="">
                                    <br><label for="all_local" class="label_chk">전체</label>
                                </div>
                            </div>
                            <div class="areamap mx-2" style="width: 20%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_city.png" />
                                <div>
                                    <input name="local_status" id="area02" type="checkbox" class="are_map" value="제주시 시내">
                                    <label for="area02" class="label_chk">제주시 시내</label>
                                </div>
                            </div>
                            <div class="areamap mx-2" style="width: 20%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_jeju_east.png" />
                                <div>
                                    <input name="local_status" id="area03" type="checkbox" class="are_map" value="제주시 동부">
                                    <label for="area03" class="label_chk">제주시 동부</label>
                                </div>
                            </div>
                            <div class="areamap mx-2" style="width: 20%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_jeju_west.png" />
                                <div>
                                    <input name="local_status" id="area04" type="checkbox" class="are_map" value="제주시 서부">
                                    <label for="area04" class="label_chk">제주시 서부</label>
                                </div>
                            </div>
                            <div class="areamap mx-2" style="width: 25%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_bt_city.png" />
                                <div>
                                    <input name="local_status" id="area05" type="checkbox" class="are_map" value="서귀포시 시내">
                                    <label for="area05" class="label_chk">서귀포시 시내</label>
                                </div>
                            </div>
                            <div class="areamap mx-2" style="width: 25%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_bt_east.png" />
                                <div>
                                    <input name="local_status" id="area06" type="checkbox" class="are_map" value="서귀포시 동부">
                                    <label for="area06" class="label_chk">서귀포시 동부</label>
                                </div>
                            </div>
                            <div class="areamap mx-2" style="width: 25%;">
                                <img src="<%= ctxPath %>/resources/images/areamap_bt_west.png" />
                                <div>
                                    <input name="local_status" id="area07" type="checkbox" class="are_map" value="서귀포시 서부">
                                    <label for="area07" class="label_chk">서귀포시 서부</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="sort-filter main" style="display: flex; justify-content:space-between; width: 98%; margin-bottom: 20px;margin-left: 3%;">
                        <div>
                            <!-- <button type="button" onclick="" class="btn btn-outline-warning btn-sm" value="">추천순</button>
                            <button type="button" onclick="" class="btn btn-outline-warning btn-sm" value="NEW">최신등록순</button> -->
                            
                            <c:if test="${sessionScope.loginuser.userid == 'admin'}">
                                <button type="button" onclick="location.href='<%= ctxPath%>/registerPlay.trip'" class="btn btn-outline-info btn-sm">즐길거리 등록</button>
                            </c:if>
                        </div>
                        <div>
                            <input type="text" name="searchWord" size="20" autocomplete="off" /> 
                            <input type="text" style="display: none;"/>
                            <button class="btn btn-outline-warning btn-sm search" type="button" title="검색">검색</button>
                        </div>
                    </div>
                </div>
                <div class='wrapper'>
                    <div class='cols' id="categoryList"></div>
                    
                    <div style="display: flex; margin-bottom: 50px;">
          				<div id="pageBar" style="margin: auto; text-align: center;"></div>
       				</div>  
                </div>
            </div>
        </div>
		 <form name="totalPlayFrm" >
	        <input type="hidden" name="str_local" />  <!--지역구분  -->
	        <input type="hidden" name="category"/>    <!--전체,관광지,체험,박물관 카테고리  -->
	        <input type="hidden" name="currentShowPageNo"/>
	        <input type="hidden" name="searchWord"/>
	    </form>	
    </div>
     
     

</body>
</html>