package com.example.test;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {
    private Long id;
    private String name;
    private String description;
    private List<TestAgendaTaskDTO> tasks;
}
