package com.example.spring_diary.Picture;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    @Autowired
    PictureRepository pictureRepository;
}
