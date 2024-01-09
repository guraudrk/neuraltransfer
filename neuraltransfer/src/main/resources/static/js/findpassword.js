const emailCheck = () => {
        const email = document.getElementById("email").value;
        const checkResult = document.getElementById("check_result");

        $.ajax({
            type: "post",
            url: "/member/email-check",
            data: { "memberEmail": email },
            success: function (res) {
                console.log("요청 성공", res); 
                if (res === "ok") {
                    checkResult.style.color = "red";
                    checkResult.innerHTML = "존재하지 않는 이메일입니다.";
                } else {
                    checkResult.style.color = "green";
                    checkResult.innerHTML = "존재하는 이메일입니다.";
                    displayPassword(email);// 이메일이 존재하는 경우 비밀번호를 보여줌.
                }
            },
            error: function (err) {
                console.log("에러발생", err);
            }
        });
    }

    // 비밀번호를 가져와서 화면에 표시하는 함수
    function displayPassword(email) {
        $.ajax({
            type: "post",
            url: "/member/get-password",
            data: { "memberEmail": email },
            success: function (password) {
                // 비밀번호가 존재하는 경우
                if (password) {
                    const passwordDisplay = document.getElementById("password_display");
                    passwordDisplay.innerHTML = "회원님의 비밀번호는 " + password + "입니다.";
                } else {
                    const passwordDisplay = document.getElementById("password_display");
                    passwordDisplay.innerHTML = "해당 이메일로 등록된 회원이 없거나 비밀번호를 찾을 수 없습니다.";
                }
            },
            error: function (err) {
                console.log("비밀번호 가져오기 실패", err);
                const passwordDisplay = document.getElementById("password_display");
                passwordDisplay.innerHTML = "비밀번호를 가져오는 중 오류가 발생했습니다.";
            }
        });
    }