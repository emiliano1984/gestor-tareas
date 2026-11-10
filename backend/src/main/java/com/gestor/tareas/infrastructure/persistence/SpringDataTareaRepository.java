package com.gestor.tareas.infrastructure.persistence;

import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTareaRepository extends JpaRepository<TareaJpaEntity, Long> {
    List<TareaJpaEntity> findByEstadoAndPrioridad(EstadoTarea estado, Prioridad prioridad);
    List<TareaJpaEntity> findByEstado(EstadoTarea estado);
    List<TareaJpaEntity> findByPrioridad(Prioridad prioridad);
}
