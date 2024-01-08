// login.js

// URL에서 파라미터 값을 추출하는 함수
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

// 페이지 로드 시 에러 메시지 확인 및 표시
window.onload = function() {
    var errorMessage = getParameterByName('error');
    if (errorMessage) {
        alert(errorMessage);
    }
};