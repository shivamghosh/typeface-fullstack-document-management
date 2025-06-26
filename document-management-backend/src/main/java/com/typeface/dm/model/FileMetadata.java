package com.typeface.dm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FILE_METADATA")
public class FileMetadata {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_SIZE")
    private int fileSize;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "CREATED_TS")
    private Date createdTs;

    @Column(name = "CREATED_BY")
    private String createdBy;


}
