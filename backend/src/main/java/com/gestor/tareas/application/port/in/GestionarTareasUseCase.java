package com.gestor.tareas.application.port.in;

import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import com.gestor.tareas.domain.model.Tarea;

import java.time.LocalDate;
import java.util.List;

public interface GestionarTareasUseCase {
    Tarea crear(String titulo, String descripcion, Prioridad prioridad, LocalDate fechaLimite);
    Tarea obtenerPorId(Long id);
    List<Tarea> listar(EstadoTarea estado, Prioridad prioridad);
    Tarea actualizar(Long id, String titulo, String descripcion, EstadoTarea estado,
                     Prioridad prioridad, LocalDate fechaLimite);
    void eliminar(Long id);
}
