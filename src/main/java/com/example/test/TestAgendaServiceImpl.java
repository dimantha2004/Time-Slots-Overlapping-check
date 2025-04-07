package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestAgendaServiceImpl implements TestAgendaService {

    private final AgendaRepository agendaRepository;
    private final TestAgendaRepository testAgendaRepository;

    @Autowired
    public TestAgendaServiceImpl(AgendaRepository agendaRepository,
                                 TestAgendaRepository testAgendaRepository) {
        this.agendaRepository = agendaRepository;
        this.testAgendaRepository = testAgendaRepository;
    }

    @Override
    public AgendaDTO createAgenda(AgendaDTO agendaDTO) {
        AgendaEntity agenda = AgendaEntity.builder()
                .name(agendaDTO.getName())
                .description(agendaDTO.getDescription())
                .build();

        AgendaEntity savedAgenda = agendaRepository.save(agenda);
        return convertToDTO(savedAgenda);
    }

    @Override
    public TestAgendaTaskDTO addTaskToAgenda(TestAgendaTaskDTO taskDTO) {
        AgendaEntity agenda = agendaRepository.findById(taskDTO.getAgendaId())
                .orElseThrow(() -> new RuntimeException("Agenda not found"));

        TestAgendaTaskEntity savedTask = testAgendaRepository.save(convertToEntity(taskDTO, agenda));
        return convertToDTO(savedTask);
    }


    @Override
    public List<TestAgendaTaskDTO> getTasksByAgenda(Long agendaId) {
        return testAgendaRepository.findByAgendaId(agendaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgendaDTO> getAllAgendas() {
        return agendaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validateTaskDTO(TestAgendaTaskDTO taskDTO) {
        if (taskDTO.getAgendaId() == null) {
            throw new IllegalArgumentException("Agenda ID must be provided");
        }
    }

//    private void checkForOverlaps(TestAgendaTaskDTO taskDTO, AgendaEntity agenda) {
//        List<TestAgendaTaskEntity> existingTasks = testAgendaRepository.findByAgendaId(agenda.getId());
//        TestAgendaTaskEntity newTask = convertToEntity(taskDTO, agenda);
//
//        if (existingTasks.stream().anyMatch(existing -> isOverlapping(newTask, existing))) {
//            throw new IllegalArgumentException("Time slot overlaps with existing task in this agenda");
//        }
//    }

//    private boolean isOverlapping(TestAgendaTaskEntity task1, TestAgendaTaskEntity task2) {
//        return task1.getStartTime().isBefore(task2.getEndTime()) &&
//                task1.getEndTime().isAfter(task2.getStartTime());
//    }

    private void validateTaskTimes(TestAgendaTaskDTO task) {
        if (task.getStartTime() == null || task.getEndTime() == null) {
            throw new IllegalArgumentException("Both start and end times must be provided");
        }
        if (task.getStartTime().isAfter(task.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }

    private AgendaDTO convertToDTO(AgendaEntity entity) {
        return AgendaDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .tasks(testAgendaRepository.findByAgendaId(entity.getId()).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private TestAgendaTaskDTO convertToDTO(TestAgendaTaskEntity entity) {
        return TestAgendaTaskDTO.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .description(entity.getDescription())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .agendaId(entity.getAgenda().getId())
                .build();
    }

    private TestAgendaTaskEntity convertToEntity(TestAgendaTaskDTO dto, AgendaEntity agenda) {
        return TestAgendaTaskEntity.builder()
                .taskName(dto.getTaskName())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .agenda(agenda)
                .build();
    }
}