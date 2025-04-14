package com.example.test;

import java.util.List;

public interface TestAgendaService {
    AgendaDTO createAgenda(AgendaDTO agendaDTO);
    TestAgendaTaskDTO addTaskToAgenda(TestAgendaTaskDTO taskDTO);
    List<TestAgendaTaskDTO> getTasksByAgenda(Long agendaId);
    List<AgendaDTO> getAllAgendas();
}