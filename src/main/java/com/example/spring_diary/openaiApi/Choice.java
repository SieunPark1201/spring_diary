package com.example.spring_diary.openaiApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Choice implements Serializable {
    private Integer index;
    private Message message;

    @JsonProperty("finish_reason")
    private String finishReason;
}
