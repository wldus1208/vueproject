package com.vue.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vue.dao.FileDao;
import com.vue.dto.ReviewDto;
import com.vue.entity.Review;

@Service
public class FileService {
    private Path imgDirPath;

    @Value("${file.storage.imgDir}")
    private String imgDir;

    @PostConstruct
    public void init() {
        this.imgDirPath = Paths.get(imgDir);
    }

    @Autowired
    private FileDao fileDao;

    @Transactional
    public void saveFiles(ReviewDto reviewDto) throws Exception {
        List<MultipartFile> files = reviewDto.getFiles();
        int id = reviewDto.getId();
        String reviewId = Integer.toString(id);
        
        if (files == null || files.isEmpty()) {
            return;
        }

        try {
            if (! Files.exists(imgDirPath)) {
                Files.createDirectories(imgDirPath);
            }

            Path reviewImgDirPath = imgDirPath.resolve(
                Paths.get(reviewId)).normalize().toAbsolutePath();

            if (! Files.exists(reviewImgDirPath)) {
                Files.createDirectories(reviewImgDirPath);
            }
        } catch (IOException e) {
            throw new Exception("Failed to make index dir", e);
        }

        for (MultipartFile file : files) {
            saveFile(file, reviewId);
        }
    }

    @Transactional
    public void saveFile(MultipartFile file, String reviewId) throws Exception {
        try {
            String originFilename = file.getOriginalFilename();
            long fileSize = file.getSize();
            String contentType = file.getContentType();
            if (file.isEmpty() || originFilename == null) {
                throw new Exception("Failed to store empty file.");
            }

            Path destinationFile = imgDirPath
                .resolve(Paths.get(reviewId))
                .resolve(Paths.get(originFilename))
                .normalize()
                .toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            String fileId = UUID.randomUUID().toString();
            fileDao.saveFile(fileId, reviewId, originFilename, fileSize, contentType);
        } catch (IOException e) {
            throw new Exception("Failed to store file", e);
        }
    }
}