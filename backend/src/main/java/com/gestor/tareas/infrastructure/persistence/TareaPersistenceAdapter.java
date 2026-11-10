package com.gestor.tareas.infrastructure.persistence;

import com.gestor.tareas.application.port.out.TareaRepositoryPort;
import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import com.gestor.tareas.domain.model.Tarea;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TareaPersistenceAdapter implements TareaRepositoryPort {
    private final SpringDataTareaRepository repository;

    public TareaPersistenceAdapter(SpringDataTareaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tarea guardar(Tarea tarea) {
        TareaJpaEntity entity = new TareaJpaEntity(tarea.id(), tarea.titulo(), tarea.descripcion(),
                tarea.estado(), tarea.prioridad(), tarea.fechaLimite(), tarea.creadaEn());
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Tarea> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Tarea> listar(EstadoTarea estado, Prioridad prioridad) {
        List<TareaJpaEntity> entities;
        if (estado != null && prioridad != null) {
            entities = repository.findByEstadoAndPrioridad(estado, prioridad);
        } else if (estado != null) {
            entities = repository.findByEstado(estado);
        } else if (prioridad != null) {
            entities = repository.findByPrioridad(prioridad);
        } else {
            entities = repository.findAll();
        }
        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private Tarea toDomain(TareaJpaEntity entity) {
        return new Tarea(entity.getId(), entity.getTitulo(), entity.getDescripcion(),
                entity.getEstado(), entity.getPrioridad(), entity.getFechaLimite(), entity.getCreadaEn());
    }
}
