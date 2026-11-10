package com.gestor.tareas.application.service;

import com.gestor.tareas.application.port.in.GestionarTareasUseCase;
import com.gestor.tareas.application.port.out.TareaRepositoryPort;
import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import com.gestor.tareas.domain.model.Tarea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GestionarTareasService implements GestionarTareasUseCase {
    private final TareaRepositoryPort repository;

    public GestionarTareasService(TareaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Tarea crear(String titulo, String descripcion, Prioridad prioridad, LocalDate fechaLimite) {
        Tarea nueva = new Tarea(null, titulo, descripcion, EstadoTarea.PENDIENTE,
                prioridad, fechaLimite, LocalDateTime.now());
        return repository.guardar(nueva);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarea obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la tarea con id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarea> listar(EstadoTarea estado, Prioridad prioridad) {
        return repository.listar(estado, prioridad);
    }

    @Override
    public Tarea actualizar(Long id, String titulo, String descripcion, EstadoTarea estado,
                            Prioridad prioridad, LocalDate fechaLimite) {
        Tarea existente = obtenerPorId(id);
        Tarea actualizada = new Tarea(existente.id(), titulo, descripcion, estado,
                prioridad, fechaLimite, existente.creadaEn());
        return repository.guardar(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        repository.eliminar(id);
    }
}
