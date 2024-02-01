package com.vue.dao;

import org.springframework.data.repository.query.Param;

public interface FileDao {
    void saveFile(
        @Param("fileId") String fileId,
        @Param("reviewId") String reviewId,
        @Param("fileName") String fileName,
        @Param("fileSize") long fileSize,
        @Param("contentType") String contentType
    );
}