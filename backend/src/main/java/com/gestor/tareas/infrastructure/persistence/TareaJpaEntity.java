package com.gestor.tareas.infrastructure.persistence;

import com.gestor.tareas.domain.model.EstadoTarea;
import com.gestor.tareas.domain.model.Prioridad;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
public class TareaJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTarea estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Prioridad prioridad;

    private LocalDate fechaLimite;
    private LocalDateTime creadaEn;

    protected TareaJpaEntity() {}

    public TareaJpaEntity(Long id, String titulo, String descripcion, EstadoTarea estado,
                          Prioridad prioridad, LocalDate fechaLimite, LocalDateTime creadaEn) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaLimite = fechaLimite;
        this.creadaEn = creadaEn;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public EstadoTarea getEstado() { return estado; }
    public Prioridad getPrioridad() { return prioridad; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public LocalDateTime getCreadaEn() { return creadaEn; }
}
