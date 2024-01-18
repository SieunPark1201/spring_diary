package com.example.spring_diary.summary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
}
