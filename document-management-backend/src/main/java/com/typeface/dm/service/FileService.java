package com.typeface.dm.service;

import com.typeface.dm.dto.FileDownloadResponse;
import com.typeface.dm.dto.FileUploadResponse;
import com.typeface.dm.exception.InternalServerErrorException;
import com.typeface.dm.exception.InvalidInputException;
import com.typeface.dm.exception.ResourceNotFoundException;
import com.typeface.dm.model.FileMetadata;
import com.typeface.dm.repository.FileMetadataRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static com.typeface.dm.common.ApplicationConstants.FILE_STORE_DIRECTORY;
import static com.typeface.dm.common.ApplicationConstants.VALID_FILE_TYPES;

@Service
public class FileService {

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public FileUploadResponse uploadFile(MultipartFile file, String uploadedBy) {
        try {
            // Generate the unique id for the file
            String fileId = UUID.randomUUID().toString();

            // Validate the file type
            String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
            if(StringUtils.isEmpty(fileType) && !VALID_FILE_TYPES.contains(fileType)) {
                throw new InvalidInputException("File Type not supported for file upload");
            }

            // Save the file content to file store
            Path dirPath = Paths.get(FILE_STORE_DIRECTORY).toAbsolutePath();
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path targetLocation = dirPath.resolve(fileId);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Save file metadata to DB
            FileMetadata fileMetadata = FileMetadata.builder()
                    .id(fileId)
                    .fileName(file.getOriginalFilename())
                    .fileSize(file.getInputStream().readAllBytes().length)
                    .createdTs(new Date())
                    .contentType(file.getContentType())
                    .fileType(fileType)
                    .createdBy(uploadedBy)
                    .isActive(true)
                    .build();
            fileMetadataRepository.save(fileMetadata);

            return new FileUploadResponse(fileId);
        } catch(IOException e) {
            throw new InternalServerErrorException("Something went wrong while processing your request", e);
        }
    }

    public List<FileMetadata> getAllFiles() {
        return fileMetadataRepository.findAll();
    }

    public FileDownloadResponse downloadFile(String fileId) {
        try {
            // Check if the file exists
            Optional<FileMetadata> fileMetadataOptional = fileMetadataRepository.findById(fileId);
            if (fileMetadataOptional.isEmpty()) {
                throw new ResourceNotFoundException("File not found!");
            }
            FileMetadata fileMetadata = fileMetadataOptional.get();

            // Read file into ByteArrayOutputStream
            ByteArrayOutputStream outputStream = getOutputStream(fileMetadata.getId());

            // Determine the content type
            String contentType = fileMetadata.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return new FileDownloadResponse(outputStream, contentType, fileMetadata.getFileName());
        } catch (IOException e) {
            throw new InternalServerErrorException("Something went wrong while processing your request", e);
        }
    }

    private ByteArrayOutputStream getOutputStream(String fileId) throws IOException {
        File file = new File(FILE_STORE_DIRECTORY, fileId);
        if (!file.exists()) {
            throw new ResourceNotFoundException("File not found!");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return outputStream;
    }
}
