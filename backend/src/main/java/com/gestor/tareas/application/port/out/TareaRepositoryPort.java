package com.gestor.tareas.application.port.out;

import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import com.gestor.tareas.domain.model.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaRepositoryPort {
    Tarea guardar(Tarea tarea);
    Optional<Tarea> buscarPorId(Long id);
    List<Tarea> listar(EstadoTarea estado, Prioridad prioridad);
    void eliminar(Long id);
}
