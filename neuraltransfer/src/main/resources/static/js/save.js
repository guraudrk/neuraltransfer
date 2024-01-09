/**
 * 
 */
const emailCheck = () =>{
    const email = document.getElementById("memberEmail").value;
    console.log("입력값: ",email)
    const checkResult = document.getElementById("check_result");
    $.ajax({
      type:"post",
      url:"/member/email-check",
      data:{
        "memberEmail":email
      },
      success:function(res){
        console.log("요청성공",res);
        if(res==="ok"){
          checkResult.style.color = "green";
          checkResult.innerHTML="사용가능한 이메일";
        } else {
          checkResult.style.color = "red";
          checkResult.innerHTML="이미 사용중인 이메일입니다.";
        }
      },
      error:function(err){
        console.log("에러발생",err);
      }
    });
  }
  
  // 비밀번호 확인을 위한 코드.
  $(document).ready(function() {
    $("#passwordConfirm").on("keyup blur", checkPasswordMatch);

    $("#signupButton").click(function(e) {
      e.preventDefault();
      
      const password = $("#password").val();
      const confirmPassword = $("#passwordConfirm").val();

      $.ajax({
        type: "post",
        url: "/save",
        data: {
          email: $("#memberEmail").val(),
          password: password,
          passwordConfirm: confirmPassword
        },
        success: function(res) {
          if (res === "회원가입이 완료되었습니다.") {
        	  alert(res); // 또는 다른 알림 방식 사용 가능
            window.location.href = "/"; //메인 페이지로 이동
          } else {
        	alert("회원가입에 실패했습니다."); // 실패 시 알림 처리
            window.location.href = "/save"; //메인 페이지로 이동
          }
        },
        error: function(err) {
          console.error("에러 발생", err);
        }
      });
    });
  });

  // 비밀번호 일치 여부 확인 함수
  function checkPasswordMatch() {
    const password = $("#password").val();
    const confirmPassword = $("#passwordConfirm").val();
    const matchResult = $("#passwordMatchResult");
    const signupButton = $("#signupButton");

    if (password === confirmPassword) {
      matchResult.css("color", "green").html("비밀번호 일치");
      signupButton.prop("disabled", false);
    } else {
      matchResult.css("color", "red").html("비밀번호 불일치");
      signupButton.prop("disabled", true);
    }
  }