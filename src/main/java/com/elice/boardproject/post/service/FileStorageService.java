package com.elice.boardproject.post.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path rootLocation = Paths.get("uploaded-images");

    public String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new IOException("Failed to store empty file " + filename);
            }
            // 파일을 저장할 위치를 확인하고, 해당 위치에 파일 복사
            Path destinationFile = this.rootLocation.resolve(filename);
            Files.createDirectories(destinationFile.getParent()); // 상위 디렉토리 생성
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new IOException("Failed to store file " + filename, e);
        }
    }

}
