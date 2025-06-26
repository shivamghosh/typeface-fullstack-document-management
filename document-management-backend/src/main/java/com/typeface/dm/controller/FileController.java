package com.typeface.dm.controller;

import com.typeface.dm.dto.FileDownloadResponse;
import com.typeface.dm.dto.FileUploadResponse;
import com.typeface.dm.model.FileMetadata;
import com.typeface.dm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * Upload a file with user info.
     */
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("uploadedBy") String uploadedBy) {

        FileUploadResponse response = fileService.uploadFile(file, uploadedBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Download a file by file ID.
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
        FileDownloadResponse response = fileService.downloadFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFileName() + "\"")
                .body(response.getStream().toByteArray());
    }

    /**
     * Get metadata for all uploaded files.
     */
    @GetMapping
    public ResponseEntity<List<FileMetadata>> getAllFiles() {
        List<FileMetadata> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }
}
