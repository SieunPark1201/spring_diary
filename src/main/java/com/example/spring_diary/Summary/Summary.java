package com.example.spring_diary.Summary;

import com.example.spring_diary.Picture.Picture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Summary {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long summaryId;

    @Column(length = 5000)
    private String content;

    @OneToMany(mappedBy = "summary", fetch = FetchType.LAZY)
    private List<Picture> pictureList;

    public Summary(String content){
        this.content = content;
    }



}
