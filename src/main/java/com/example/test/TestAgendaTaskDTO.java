package com.example.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAgendaTaskDTO {
    private Long id;
    private String taskName;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long agendaId;
}