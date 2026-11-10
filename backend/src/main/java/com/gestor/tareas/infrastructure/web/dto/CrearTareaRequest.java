package com.gestor.tareas.infrastructure.web.dto;

import com.gestor.tareas.domain.model.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CrearTareaRequest(
        @NotBlank @Size(max = 100) String titulo,
        @Size(max = 500) String descripcion,
        @NotNull Prioridad prioridad,
        LocalDate fechaLimite
) {}
