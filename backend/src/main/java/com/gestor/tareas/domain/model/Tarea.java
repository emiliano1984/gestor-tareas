package com.gestor.tareas.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Tarea(
        Long id,
        String titulo,
        String descripcion,
        EstadoTarea estado,
        Prioridad prioridad,
        LocalDate fechaLimite,
        LocalDateTime creadaEn
) {
    public Tarea {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (titulo.length() > 100) {
            throw new IllegalArgumentException("El título no puede superar 100 caracteres");
        }
        if (descripcion != null && descripcion.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede superar 500 caracteres");
        }
        if (estado == null || prioridad == null) {
            throw new IllegalArgumentException("El estado y la prioridad son obligatorios");
        }
    }

    public Tarea conId(Long nuevoId, LocalDateTime fechaCreacion) {
        return new Tarea(nuevoId, titulo, descripcion, estado, prioridad, fechaLimite,
                fechaCreacion == null ? creadaEn : fechaCreacion);
    }
}
