package com.board.api.entity;

import com.board.api.enums.FileStatus;
import com.board.api.enums.FileTargetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Files extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member uploader;

    @Enumerated(EnumType.STRING)
    @Column(name="file_type",nullable = false)
    private FileTargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name="upload_dir", nullable = false)
    private String uploadDir;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name="stored_name", nullable = false, unique = true)
    private String storedName;

    @Column(nullable = false)
    private String url;

    @Column(name="file_size", nullable = false)
    private Long size;

    @Enumerated(EnumType.STRING)
    private FileStatus status;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    public void delete() {
        this.deletedDate = LocalDateTime.now();
    }

    public Files(
            Member member,
            FileTargetType targetType,
            Long targetId,
            String uploadDir,
            String originalName,
            String storedName,
            String url,
            Long size
    ) {
        this.uploader = member;
        this.targetType = targetType;
        this.targetId = targetId;
        this.uploadDir = uploadDir;
        this.originalName = originalName;
        this.storedName = storedName;
        this.url = url;
        this.size = size;
        this.status = FileStatus.ACTIVE;
    }

}
