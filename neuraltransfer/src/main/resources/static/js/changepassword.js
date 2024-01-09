function validateForm() {
            var password = document.getElementById("newPassword").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var mismatchMessage = document.getElementById("passwordMismatch");

            if (password !== confirmPassword) {
                mismatchMessage.innerHTML = "비밀번호가 맞지 않습니다.";
                mismatchMessage.style.color = "red"; // 빨간색으로 설정
               return false;
            } else {
                mismatchMessage.innerHTML = "비밀번호가 일치합니다.";
                mismatchMessage.style.color = "green"; // 초록색으로 설정
                
         // 비밀번호 변경이 성공했을 때 alert 띄우기
        if (mismatchMessage.style.color === "green") {
            alert("비밀번호 변경이 성공적으로 완료되었습니다!");
        }
                
                return true;
              
            }
        }
 