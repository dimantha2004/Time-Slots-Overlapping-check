package com.example.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAgendaRepository extends JpaRepository<TestAgendaTaskEntity, Long> {
    List<TestAgendaTaskEntity> findByAgendaId(Long agendaId);
}