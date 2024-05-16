package com.example.spring_diary.openaiApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
public class Message implements Serializable {

    private String role;
    private String content;
}
