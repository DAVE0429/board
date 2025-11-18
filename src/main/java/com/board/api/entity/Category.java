package com.board.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@ToString
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category")
    @JoinColumn(name = "board_id")
    private List<Board> boards = new ArrayList<>();
}




