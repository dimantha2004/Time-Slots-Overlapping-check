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
    public ResponseEntity<String> addTaskToAgenda(
            @PathVariable Long agendaId,
            @RequestBody TestAgendaTaskDTO taskDTO) {

        taskDTO.setAgendaId(agendaId);

        List<TestAgendaTaskDTO> existingTasks = agendaService.getTasksByAgenda(agendaId);

        for (TestAgendaTaskDTO existing : existingTasks) {
            if (isOverlapping(taskDTO, existing)) {
                return ResponseEntity.badRequest().body("time is overlapping");
            }
        }

        TestAgendaTaskDTO result = agendaService.addTaskToAgenda(taskDTO);
        return ResponseEntity.ok("Task added successfully");
    }

    private boolean isOverlapping(TestAgendaTaskDTO newTask, TestAgendaTaskDTO existing) {
        return newTask.getStartTime().isBefore(existing.getEndTime()) &&
                newTask.getEndTime().isAfter(existing.getStartTime());
    }

    @GetMapping("/{agendaId}/tasks")
    public ResponseEntity<List<TestAgendaTaskDTO>> getTasksByAgenda(
            @PathVariable Long agendaId) {
        return ResponseEntity.ok(agendaService.getTasksByAgenda(agendaId));
    }
}