package com.board.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category")
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Category (String name){
        this.name = name;
    }

    public void update(@NotEmpty(message = "카테고리를 입력해주세요.") String name){
        this.name = this.name;
    }

}




