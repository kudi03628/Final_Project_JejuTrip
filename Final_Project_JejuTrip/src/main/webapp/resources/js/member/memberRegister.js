let checkUserid = false;
let checkName = false;
let checkPw = false;
let checkPwCheck = false;
let checkEmailId = false;
let checkMobile = false;

$(function () {

    $("span.error").hide();
    
    // 생년월일 Date Picker
    $('.datepicker').daterangepicker({
        singleDatePicker: true,
         locale: {
            "format": 'YYYY-MM-DD',
            "applyLabel": "확인",
            "cancelLabel": "취소",
            "daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
            "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
         },
        showDropdowns: true,
        minYear: 1900,
        maxYear: 2025,
        maxDate: moment()
    }, function(start, end, label) {
        // 생년월일 선택 후의 콜백 함수
        const selectedDate = start.format('YYYY-MM-DD');
        const today = moment().format('YYYY-MM-DD');
    
        if (selectedDate === today) {
            alert("생년월일은 오늘 날짜 이전으로만 선택 가능합니다.");
            // 선택된 값을 초기화
            $("input#birthday").val("");
        }
    });
  
    // 생년월일 키보드 입력 막기
    $("input#birthday").on("keypress keydown keyup", function(e) {
        e.preventDefault();
    });

    // 주소 클릭 시
    $('input#address').click(function () {

        new daum.Postcode({
            oncomplete: function (data) {

                let addr = '';

                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }

                document.getElementById("address").value = addr;
                document.getElementById("detail_address").focus();

            }
        }).open();

    });

    // ===== 아이디 유효성 검사 =====
    $("input#userid").blur((e) => {

        const userid = $(e.target).val().trim();

        const regExp_userid = new RegExp(/^(?=.*[A-Za-z])[A-Za-z0-9]{5,20}$/);
        const bool = regExp_userid.test(userid);

        if(userid == "") {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("아이디를 입력해주세요.");
            checkUserid = false;

        } else if(!bool) {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("아이디는 5~20자 이내의 영문, 숫자로만 입력해주세요.");
            checkUserid = false;

        } else {

            // 아이디 중복 확인
            $.ajax({
                url: "useridDuplicateCheck.trip",
                type: "post",
                data: {"userid":$(e.target).val()},
                dataType: "json",
                success: function(json) {
                    if(json.isExist) {
                        $(e.target).addClass("input_error");
                        $(e.target).next().show();
                        $(e.target).next().text("중복된 아이디입니다. 다시 입력해주세요.");
                        checkUserid = false;
                        
                    } else {
                        $(e.target).removeClass("input_error");
                        $(e.target).next().hide();
                        checkUserid = true;
                    }
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });

        }
    });

    // ===== 성명 유효성 검사 =====
    $("input#user_name").blur((e) => {

        const name = $(e.target).val().trim();

        const regExp_name = new RegExp(/^[가-힣]{2,6}$/);
        const bool = regExp_name.test(name);

        if(name == "") {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("성명을 입력해주세요.");
            checkName = false;

        } else if(!bool) {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("성명은 2~6자 이내 한글로만 입력해주세요.");
            checkName = false;

        } else {
            $(e.target).removeClass("input_error");
            $(e.target).next().hide();
            checkName = true;
        }
    });

    // ===== 비밀번호 유효성 검사 =====
    $("input#pw").blur((e) => {

        const pw = $(e.target).val().trim();

        const regExp_pw = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
        const bool = regExp_pw.test(pw);

        if(pw == "") {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("비밀번호를 입력해주세요.");
            checkPw = false;

        } else if(!bool) {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).val("");
            $(e.target).next().text("비밀번호는 조건에 맞게 입력해주세요.");
            checkPw = false;

        } else {
            $(e.target).removeClass("input_error");
            $(e.target).next().hide();
            checkPw = true;
        }

    });

    // ===== 비밀번호 확인 유효성 검사 =====
    $("input#pwCheck").blur((e) => {

        const pw = $("input#pw").val();
        const pwCheck = $(e.target).val().trim();

        if(pwCheck == "") {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("비밀번호 확인을 입력해주세요.");
            checkPwCheck = false;

        } else if(pw != pwCheck) {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("비밀번호가 일치하지 않습니다.");
            checkPwCheck = false;

        } else {
            $(e.target).removeClass("input_error");
            $(e.target).next().hide();
            checkPwCheck = true;
        }
    });

    // ===== 이메일 유효성 검사 =====
    $("input#email_id").blur((e) => {

        const email_id = $(e.target).val().trim();

        const regExp_emailId = new RegExp(/^(?=.*[A-Za-z])[A-Za-z0-9]{5,20}$/);
        const bool = regExp_emailId.test(email_id);

        if(email_id == "") {
            $(e.target).addClass("input_error");
            $(e.target).parent().next().show();
            $(e.target).parent().next().text("이메일 아이디를 입력해주세요.");
            checkEmailId = false;

        } else if(!bool) {
            $(e.target).addClass("input_error");
            $(e.target).parent().next().show();
            $(e.target).parent().next().text("이메일 아이디는 5~20자 이내의 영문, 숫자로만 입력해주세요.");
            checkEmailId = false;

        } else {

            $.ajax({
                url: "userEmailDuplicateCheck.trip",
                type: "post",
                data: {"email":$(e.target).val() + "@" + $(e.target).next().next().val()},
                dataType: "json",
                success: function(json) {
                    if(json.isExist) {
                        $(e.target).addClass("input_error");
                        $(e.target).parent().next().show();
                        $(e.target).parent().next().text("중복된 이메일입니다. 다시 입력해주세요.");
                        checkEmailId = false;
                        
                    } else {
                        $(e.target).removeClass("input_error");
                        $(e.target).parent().next().hide();
                        checkEmailId = true;
                    }
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });
        }

    });

    $("select#email_dropdown").change(function(e) {

        $.ajax({
            url: "userEmailDuplicateCheck.trip",
            type: "post",
            data: {"email":$(e.target).prev().prev().val() + "@" + $(e.target).val()},
            dataType: "json",
            success: function(json) {
                if(json.isExist) {
                    $(e.target).prev().prev().addClass("input_error");
                    $(e.target).parent().next().show();
                    $(e.target).parent().next().text("중복된 이메일입니다. 다시 입력해주세요.");
                    checkEmailId = false;
                    
                } else {
                    $(e.target).prev().prev().removeClass("input_error");
                    $(e.target).parent().next().hide();
                    checkEmailId = true;
                }
            },
            error: function(request, status, error) {
                alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
            }
        });

    });


    // ===== 휴대폰 유효성 검사 =====
    $("input#mobile").blur((e) => {

        const mobile = $(e.target).val().trim();

        const regExp_mobile = new RegExp(/^01[016789]{1}[0-9]{3,4}[0-9]{4}$/);
        const bool = regExp_mobile.test(mobile);

        if(mobile == "") {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("휴대폰 번호를 입력해주세요.");
            checkMobile = false;

        } else if(!bool) {
            $(e.target).addClass("input_error");
            $(e.target).next().show();
            $(e.target).next().text("유효하지 않은 연락처입니다.");
            checkMobile = false;

        } else {
            
            $.ajax({
                url: "userMobileDuplicateCheck.trip",
                type: "post",
                data: {"mobile": $(e.target).val()},
                dataType: "json",
                success: function(json) {
                    if(json.isExist) {
                        $(e.target).addClass("input_error");
                        $(e.target).next().show();
                        $(e.target).next().text("중복된 연락처입니다.");
                        checkMobile = false;

                    } else {
                        $(e.target).removeClass("input_error");
                        $(e.target).next().hide();
                        checkMobile = true;
                    }
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });

        }

    });

    // 주소 키보드 입력 막기
    $("input#address").on("keypress", function(e) {
        e.preventDefault();
    });


    $("input#detail_address").keyup(function(e) {
        if(e.keyCode == 13) {
            goRegister();
        }
    });

});


function goRegister(ctxPath) {

    if(checkUserid && checkName && checkPw && checkPwCheck && checkEmailId && checkMobile) {

        const email_dropdown = $("select#email_dropdown").val();

        if(email_dropdown == "") {
            alert("이메일 주소를 선택해주세요.");
            return;
        }

        // 생년월일 값 확인
        const birthday = $("input#birthday").val().trim();
        const today = new Date().toISOString().split('T')[0]; // 오늘 날짜를 YYYY-MM-DD 형식으로 변환

        if (birthday == today) {
            alert("생년월일을 선택해주세요.");
            return;
        }

        const address = $("input#address").val().trim();
        const detail_address = $("input#detail_address").val().trim();

        if(address == "") {
            alert("주소를 입력해주세요.");
            return;

        } else if(detail_address == "") {
            alert("상세주소를 입력해주세요.");
            return;
        }

        // const frm = document.registerFrm;
        // frm.action = "";
        // frm.method = "post";
        // // frm.submit();

        let queryString = $("form[name='registerFrm']").serialize();
        
        const email = $("input#email_id").val() + "@" + email_dropdown;

        // email 값을 쿼리 문자열에 추가
        if (queryString) {
            queryString += "&";
        }
        queryString += "email=" + encodeURIComponent(email);
        
        // 회원가입 처리하기
        $.ajax({
            url: ctxPath + "/memberRegisterEnd.trip",
            type: "post",
            data: queryString,
            dataType: "json",
            success: function(json) {
                if(json.n == 1) {
                    alert("회원가입이 성공되었습니다.");
                    location.href = ctxPath + "/index.trip";

                } else {
                    alert("회원가입에 실패했습니다.");
                    history.back();
                }
            },
            error: function(request, status, error) {
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });

    } else {
        alert("가입 정보를 모두 입력하세요.");
        return;
    }

}