package com.example.spring_diary.diary;


import com.example.spring_diary.summary.Summary;
import com.example.spring_diary.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long diaryId;

    @Column(length = 5000)
    private String content;

    @Column
    private LocalDate date;

    @Column
    private boolean uploaded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY)
    private List<Summary> summaryList;


    public Diary(String content, LocalDate date, boolean uploaded) {
        this.content = content;
        this.date = date;
        this.uploaded = uploaded;
    }


}
