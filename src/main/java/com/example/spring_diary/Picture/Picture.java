package com.example.spring_diary.Picture;

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

    public Picture(byte[] content){
        this.content = content;
    }


}
