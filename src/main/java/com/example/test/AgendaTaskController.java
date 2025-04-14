package com.example.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaTaskController {

    private final TestAgendaService agendaService;

    public AgendaTaskController(TestAgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<?> createAgenda(@RequestBody AgendaDTO agendaDTO) {
        try {
            AgendaDTO createdAgenda = agendaService.createAgenda(agendaDTO);
            return ResponseEntity.ok(createdAgenda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> getAllAgendas() {
        return ResponseEntity.ok(agendaService.getAllAgendas());
    }

    @PostMapping("/{agendaId}/tasks")
    public ResponseEntity<?> addTaskToAgenda(
            @PathVariable Long agendaId,
            @RequestBody TestAgendaTaskDTO taskDTO) {
        try {
            taskDTO.setAgendaId(agendaId);
            TestAgendaTaskDTO result = agendaService.addTaskToAgenda(taskDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add task: " + e.getMessage());
        }
    }

    @GetMapping("/{agendaId}/tasks")
    public ResponseEntity<List<TestAgendaTaskDTO>> getTasksByAgenda(@PathVariable Long agendaId) {
        return ResponseEntity.ok(agendaService.getTasksByAgenda(agendaId));
    }
}