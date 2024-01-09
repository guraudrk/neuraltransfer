  $(document).ready(function(){
        	var message = /*[[${#strings.isEmpty(successMessage) ? errorMessage : successMessage}]]*/ "";
            var alertType = /*[[${alertType}]]*/ "";

            showAlert(message, alertType);
        });

        function showAlert(message, alertType) {
            var alertDiv = $('<div class="alert alert-' + alertType + ' alert-dismissible fade show" role="alert">' +
                             '<strong>' + message + '</strong>' +
                             '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
                             '<span aria-hidden="true">&times;</span></button></div>');

            // 알림창을 body에 추가
            $('body').append(alertDiv);
        }
    

    
    
        var loggedIn = /*[[${loggedIn}]]*/ false;

        function loginButtonAction() {
            if (loggedIn === true) {
                showAlert('이미 로그인 되어 있습니다.');
            } else {
                location.reload();
                window.location.href = '/login';
            }
        }

        function displayImage(inputId, boxId) {
            var input = document.getElementById(inputId);
            var box = document.getElementById(boxId);
            var text = box.querySelector('.upload-text');

            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    box.style.backgroundImage = 'url(' + e.target.result + ')';
                    box.style.backgroundSize = 'cover';
                    text.style.display = 'none';
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
 