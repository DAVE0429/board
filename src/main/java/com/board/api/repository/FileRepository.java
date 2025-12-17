package com.board.api.repository;

import com.board.api.dto.file.FileDto;
import com.board.api.entity.Files;
import com.board.api.entity.Member;
import com.board.api.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileRepository extends JpaRepository<Files, Long> {
List<FileDto> findByTargetTypeAndTargetId(TargetType targetType, Long targetId);


}
