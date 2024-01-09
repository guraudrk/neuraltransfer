function downloadImage(imageUrl) {
        var link = document.createElement('a'); //앵커 앨리먼트 생성.
        link.href = imageUrl; //이미지의 url을 설정한다.
        link.download = 'image.jpg'; //파일의 이름을 설정한다.
        document.body.appendChild(link); //엘리먼트를 현재 페이지의 body에 추가한다.
        link.click();//앨리먼트를 클릭.
        document.body.removeChild(link);//다운로드가 시작되었으면 페이지에는 무용.
    }