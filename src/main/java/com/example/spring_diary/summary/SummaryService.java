package com.example.spring_diary.summary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    @Autowired
    SummaryRepository summaryRepository;
}
