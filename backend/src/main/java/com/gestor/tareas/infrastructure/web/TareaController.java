package com.gestor.tareas.infrastructure.web;

import com.gestor.tareas.application.port.in.GestionarTareasUseCase;
import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import com.gestor.tareas.domain.model.Tarea;
import com.gestor.tareas.infrastructure.web.dto.ActualizarTareaRequest;
import com.gestor.tareas.infrastructure.web.dto.CrearTareaRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "http://localhost:5173")
public class TareaController {
    private final GestionarTareasUseCase gestionarTareas;

    public TareaController(GestionarTareasUseCase gestionarTareas) {
        this.gestionarTareas = gestionarTareas;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarea crear(@Valid @RequestBody CrearTareaRequest request) {
        return gestionarTareas.crear(request.titulo(), request.descripcion(),
                request.prioridad(), request.fechaLimite());
    }

    @GetMapping
    public List<Tarea> listar(
            @RequestParam(required = false) EstadoTarea estado,
            @RequestParam(required = false) Prioridad prioridad) {
        return gestionarTareas.listar(estado, prioridad);
    }

    @GetMapping("/{id}")
    public Tarea obtener(@PathVariable Long id) {
        return gestionarTareas.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Tarea actualizar(@PathVariable Long id,
                            @Valid @RequestBody ActualizarTareaRequest request) {
        return gestionarTareas.actualizar(id, request.titulo(), request.descripcion(),
                request.estado(), request.prioridad(), request.fechaLimite());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        gestionarTareas.eliminar(id);
    }
}
