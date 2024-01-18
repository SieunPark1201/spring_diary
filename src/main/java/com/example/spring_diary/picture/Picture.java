package com.example.spring_diary.picture;

import com.example.spring_diary.summary.Summary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pictureId;

    @Column
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "summary_summaryId")
    private Summary summary;

    public Picture(byte[] content){
        this.content = content;
    }


}
