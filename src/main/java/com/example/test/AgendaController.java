package com.example.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final TestAgendaService agendaService;

    public AgendaController(TestAgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> createAgenda(@RequestBody AgendaDTO agendaDTO) {
        return ResponseEntity.ok(agendaService.createAgenda(agendaDTO));
    }

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> getAllAgendas() {
        return ResponseEntity.ok(agendaService.getAllAgendas());
    }

    @PostMapping("/{agendaId}/tasks")
    public ResponseEntity<TestAgendaTaskDTO> addTaskToAgenda(
            @PathVariable Long agendaId,
            @RequestBody TestAgendaTaskDTO taskDTO) {
        taskDTO.setAgendaId(agendaId);
        return ResponseEntity.ok(agendaService.addTaskToAgenda(taskDTO));
    }

    @GetMapping("/{agendaId}/tasks")
    public ResponseEntity<List<TestAgendaTaskDTO>> getTasksByAgenda(
            @PathVariable Long agendaId) {
        return ResponseEntity.ok(agendaService.getTasksByAgenda(agendaId));
    }
}