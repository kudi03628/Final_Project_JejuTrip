<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
    body {
        font-family: "Noto Sans KR", sans-serif;
        font-optical-sizing: auto;
    }
    div.container {
        width: 45%;
        margin: 5% auto;
        border: solid 1px rgba(0, 0, 0, 0.15);
        border-radius: 40px;
        box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
    }
    div.info {
        width: 100%;
        margin: 10% 5% 0 0;
    }

    div.info input {
        display: block;
        width: 100%;
        max-width: 680px;
        height: 50px;
        margin: 0 auto;
        margin-bottom: 4%;
        border-radius: 8px;
        border: solid 1px rgba(15, 19, 42, .1);
        padding: 0 0 0 15px;
        font-size: 16px;
    }

    button#pwUpdateBtn {
        width: 100%;
        height: 50px;
        margin: 1% auto;
        border-radius: 8px;
        border-color: #ff5000;
        color: #ff5000;
    }

    button#pwUpdateBtn:hover {
        background-color: #ff5000;
        color: white;
    }
</style>

<script type="text/javascript">
    $(document).ready(function() {

        $("input#pwCheck").keyup(function(e) {
            if(e.keyCode == 13) {
                goPwUpdate();
            }
        });
        
    });

    function goPwUpdate() {
        const pw = $("input#pw").val().trim();
        const pwCheck = $("input#pwCheck").val().trim();

        if(pw == "") {
            alert("비밀번호를 입력해주세요!");
            return;
        }

        const regExp_pw = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
        const bool = regExp_pw.test(pw);

        if(!bool) {
            alert("올바른 비밀번호 형식이 아닙니다.\n비밀번호를 다시 입력해주세요.");
            $("input#pw").val("");
            $("input#pw").focus();
            return;
        }

        if(pwCheck == "") {
            alert("비밀번호 확인을 입력해주세요!");
            return;
        }

        if(pw != pwCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            $("input#pwCheck").val("");
            $("input#pwCheck").focus();
        }
    }
</script>

<div class="container">
    <div style="width: 80%; margin: 7% auto;">
        <h2 style="margin-top: 20%;" class="font-weight-bold">비밀번호 찾기</h2>
        <h5>비밀번호 변경</h5>

        <form name="pwFindEndFrm">
        
            <div class="d-flex justify-content-center mt-5">
               <div class="form-check form-check-inline">
                 <input class="form-check-input" type="radio" name="memberType" id="inlineRadio1" value="member" checked>
                 <label class="form-check-label" for="inlineRadio1">일반회원</label>
               </div>
               <div class="form-check form-check-inline pl-3">
                 <input class="form-check-input" type="radio" name="memberType" id="inlineRadio2" value="company">
                 <label class="form-check-label" for="inlineRadio2">업체회원</label>
               </div>
               <div class="form-check form-check-inline pl-3">
                 <input class="form-check-input" type="radio" name="memberType" id="inlineRadio3" value="admin">
                 <label class="form-check-label" for="inlineRadio3">관리자</label>
               </div>
            </div>
        
            <div class="info">
                <input type="password" name="pw" id="pw" placeholder="비밀번호">
                <input type="password" name="pwCheck" id="pwCheck" placeholder="비밀번호 재입력">
            </div>
            
            <div class="mt-5" style="margin-bottom: 20%;">
                <button type="button" class="btn" id="pwUpdateBtn" onclick="goPwUpdate()">변경하기</button>
            </div>
        </form>
    </div>
</div>
