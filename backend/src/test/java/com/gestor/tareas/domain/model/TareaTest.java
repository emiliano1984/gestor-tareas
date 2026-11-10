package com.gestor.tareas.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TareaTest {
    @Test
    void noDebePermitirTituloVacio() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tarea(null, " ", null, EstadoTarea.PENDIENTE,
                        Prioridad.MEDIA, LocalDate.now(), null));
    }

    @Test
    void debeCrearTareaValida() {
        Tarea tarea = new Tarea(null, "Entregar actividad", "Preparar proyecto",
                EstadoTarea.PENDIENTE, Prioridad.ALTA, LocalDate.now(), null);
        assertEquals("Entregar actividad", tarea.titulo());
        assertEquals(Prioridad.ALTA, tarea.prioridad());
    }
}
