package service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class ImageService {
	
	//이미지를 업로드하고 폴더에 저장하는 로직이다.
	
	@Autowired
    private S3AsyncClient S3Client;

    @Value("${aws.s3.bucket}") //알맞은 버켓 이름을 설정한다.
    private String bucketName = "neuraltransfer";

    public void saveImage(MultipartFile image, String email) {
        // 이미지를 S3에 업로드하는 로직 구현
        // 폴더 생성 및 업로드 등의 작업 수행
    	try {
            // S3에 업로드할 폴더 경로. email값을 넣는다.
            String folderPath = email + "/";
            
            // 폴더 생성 (이미 존재하면 무시)
            createFolderIfNotExists(folderPath);

            // 파일명 설정
            String fileName = folderPath + Objects.requireNonNull(image.getOriginalFilename());

            // S3에 이미지 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            S3Client.putObject(putObjectRequest, AsyncRequestBody.fromFile(getFileFromMultipartFile(image)))
                    .thenAccept(response -> {
                        // 업로드 성공 시 처리
                    })
                    .exceptionally(throwable -> {
                        // 업로드 실패 시 처리
                        return null;
                    }).join();  // 비동기 작업이 완료될 때까지 기다림
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
        }
    }

 // MultipartFile을 File로 변환
    private File getFileFromMultipartFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", null);
        multipartFile.transferTo(file);
        return file;
    }

    // S3에 폴더 생성 (이미 존재하면 무시)
    private void createFolderIfNotExists(String folderPath) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(folderPath)
                .build();

        try {
        	S3Client.headObject(headObjectRequest).join();
        } catch (NoSuchKeyException e) {
            // 폴더가 없으면 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderPath)
                    .build();

            S3Client.putObject(putObjectRequest, AsyncRequestBody.fromString(""))
                    .join();  // 비동기 작업이 완료될 때까지 기다림
        }
    }
    
    
    //s3에서 해당 계정의 이미지 목록을 가져온다.
    public List<String> getUserImages(String email) {
        // S3에서 해당 계정의 이미지 목록을 가져옴
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(email + "/")
                .build();

        ListObjectsV2Response listObjectsResponse = S3Client.listObjectsV2(listObjectsRequest).join();

        return listObjectsResponse.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}
