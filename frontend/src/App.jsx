import { useEffect, useMemo, useState } from 'react'
import { CheckCircle2, Circle, ClipboardList, Plus, Search, Trash2, Pencil, X, ListTodo } from 'lucide-react'
import { tareasApi } from './api/tareasApi'

const initialForm = {
  titulo: '', descripcion: '', prioridad: 'MEDIA', estado: 'PENDIENTE', fechaLimite: ''
}

const statusLabels = {
  PENDIENTE: 'Pendiente',
  EN_PROGRESO: 'En progreso',
  COMPLETADA: 'Completada'
}

function App() {
  const [tareas, setTareas] = useState([])
  const [form, setForm] = useState(initialForm)
  const [editingId, setEditingId] = useState(null)
  const [estadoFiltro, setEstadoFiltro] = useState('')
  const [prioridadFiltro, setPrioridadFiltro] = useState('')
  const [busqueda, setBusqueda] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  async function cargarTareas() {
    try {
      setError('')
      const data = await tareasApi.listar({ estado: estadoFiltro, prioridad: prioridadFiltro })
      setTareas(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { cargarTareas() }, [estadoFiltro, prioridadFiltro])

  const visibles = useMemo(() => tareas.filter(t =>
    `${t.titulo} ${t.descripcion || ''}`.toLowerCase().includes(busqueda.toLowerCase())
  ), [tareas, busqueda])

  const resumen = {
    total: tareas.length,
    pendientes: tareas.filter(t => t.estado === 'PENDIENTE').length,
    progreso: tareas.filter(t => t.estado === 'EN_PROGRESO').length,
    completadas: tareas.filter(t => t.estado === 'COMPLETADA').length
  }

  function cambiarCampo(event) {
    setForm(current => ({ ...current, [event.target.name]: event.target.value }))
  }

  async function guardar(event) {
    event.preventDefault()
    try {
      setError('')
      const payload = {
        ...form,
        fechaLimite: form.fechaLimite || null
      }
      if (editingId) await tareasApi.actualizar(editingId, payload)
      else await tareasApi.crear(payload)
      setForm(initialForm)
      setEditingId(null)
      await cargarTareas()
    } catch (err) {
      setError(err.message)
    }
  }

  function editar(tarea) {
    setEditingId(tarea.id)
    setForm({
      titulo: tarea.titulo,
      descripcion: tarea.descripcion || '',
      prioridad: tarea.prioridad,
      estado: tarea.estado,
      fechaLimite: tarea.fechaLimite || ''
    })
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  async function eliminar(id) {
    if (!window.confirm('¿Deseas eliminar esta tarea?')) return
    try {
      await tareasApi.eliminar(id)
      await cargarTareas()
    } catch (err) {
      setError(err.message)
    }
  }

  async function completar(tarea) {
    try {
      const nuevoEstado = tarea.estado === 'COMPLETADA' ? 'PENDIENTE' : 'COMPLETADA'
      await tareasApi.actualizar(tarea.id, {
        titulo: tarea.titulo,
        descripcion: tarea.descripcion,
        estado: nuevoEstado,
        prioridad: tarea.prioridad,
        fechaLimite: tarea.fechaLimite
      })
      await cargarTareas()
    } catch (err) {
      setError(err.message)
    }
  }

  function cancelarEdicion() {
    setEditingId(null)
    setForm(initialForm)
  }

  return (
    <main className="app-shell">
      <header className="topbar">
        <a className="brand" href="#"><span className="brand-icon"><ListTodo size={22} /></span> Task<span>Manager</span></a>
        <span className="topbar-note">Organiza tu trabajo, alcanza tus metas</span>
      </header>

      <section className="hero">
        <div>
          <p className="eyebrow">PANEL DE CONTROL</p>
          <h1>Mis tareas</h1>
          <p className="subtitle">Mantén tus actividades organizadas y bajo control.</p>
        </div>
        <div className="hero-mark"><ClipboardList size={38} /></div>
      </section>

      <section className="stats">
        <article className="stat-card"><span className="stat-label">Total de tareas</span><strong>{resumen.total}</strong><span className="stat-icon blue"><ClipboardList size={19} /></span></article>
        <article className="stat-card"><span className="stat-label">Pendientes</span><strong>{resumen.pendientes}</strong><span className="stat-icon amber"><Circle size={19} /></span></article>
        <article className="stat-card"><span className="stat-label">En progreso</span><strong>{resumen.progreso}</strong><span className="stat-icon violet"><Pencil size={19} /></span></article>
        <article className="stat-card"><span className="stat-label">Completadas</span><strong>{resumen.completadas}</strong><span className="stat-icon green"><CheckCircle2 size={19} /></span></article>
      </section>

      {error && <div className="alert">{error}</div>}

      <section className="content-grid">
        <form className="panel form-panel" onSubmit={guardar}>
          <div className="panel-heading">
            <div><p className="eyebrow">{editingId ? 'ACTUALIZAR' : 'NUEVA ACTIVIDAD'}</p><h2>{editingId ? 'Editar tarea' : 'Crear tarea'}</h2></div>
            {editingId && <button type="button" className="icon-button" onClick={cancelarEdicion} aria-label="Cancelar"><X size={18} /></button>}
          </div>
          <label>Título<input name="titulo" value={form.titulo} onChange={cambiarCampo} placeholder="Ej. Preparar informe" maxLength="100" required /></label>
          <label>Descripción<textarea name="descripcion" value={form.descripcion} onChange={cambiarCampo} placeholder="Agrega detalles de la actividad..." rows="4" maxLength="500" /></label>
          <div className="form-row">
            <label>Prioridad<select name="prioridad" value={form.prioridad} onChange={cambiarCampo}><option value="BAJA">Baja</option><option value="MEDIA">Media</option><option value="ALTA">Alta</option></select></label>
            {editingId && <label>Estado<select name="estado" value={form.estado} onChange={cambiarCampo}><option value="PENDIENTE">Pendiente</option><option value="EN_PROGRESO">En progreso</option><option value="COMPLETADA">Completada</option></select></label>}
          </div>
          <label>Fecha límite<input type="date" name="fechaLimite" value={form.fechaLimite} onChange={cambiarCampo} /></label>
          <button className="primary-button" type="submit"><Plus size={18} />{editingId ? 'Guardar cambios' : 'Agregar tarea'}</button>
        </form>

        <section className="panel list-panel">
          <div className="list-heading"><div><p className="eyebrow">SEGUIMIENTO</p><h2>Lista de tareas</h2></div><span className="task-count">{visibles.length} actividades</span></div>
          <div className="toolbar">
            <div className="search-box"><Search size={17} /><input value={busqueda} onChange={e => setBusqueda(e.target.value)} placeholder="Buscar tareas..." /></div>
            <select value={estadoFiltro} onChange={e => setEstadoFiltro(e.target.value)} aria-label="Filtrar por estado"><option value="">Todos los estados</option><option value="PENDIENTE">Pendiente</option><option value="EN_PROGRESO">En progreso</option><option value="COMPLETADA">Completada</option></select>
            <select value={prioridadFiltro} onChange={e => setPrioridadFiltro(e.target.value)} aria-label="Filtrar por prioridad"><option value="">Todas las prioridades</option><option value="BAJA">Baja</option><option value="MEDIA">Media</option><option value="ALTA">Alta</option></select>
          </div>

          {loading ? <div className="empty-state">Cargando tareas...</div> :
            visibles.length === 0 ? <div className="empty-state"><ClipboardList size={34} /><strong>No hay tareas para mostrar</strong><span>Crea una tarea o cambia los filtros.</span></div> :
            <div className="task-list">{visibles.map(tarea => (
              <article className={`task-item ${tarea.estado === 'COMPLETADA' ? 'is-done' : ''}`} key={tarea.id}>
                <button className="complete-button" onClick={() => completar(tarea)} title="Cambiar estado" type="button">{tarea.estado === 'COMPLETADA' ? <CheckCircle2 size={22} /> : <Circle size={22} />}</button>
                <div className="task-main"><div className="task-title-row"><h3>{tarea.titulo}</h3><span className={`priority priority-${tarea.prioridad.toLowerCase()}`}>{tarea.prioridad}</span></div>
                  {tarea.descripcion && <p>{tarea.descripcion}</p>}
                  <div className="task-meta"><span className={`status status-${tarea.estado.toLowerCase()}`}>{statusLabels[tarea.estado]}</span>{tarea.fechaLimite && <span>Vence: {tarea.fechaLimite}</span>}</div>
                </div>
                <div className="task-actions"><button className="icon-button" onClick={() => editar(tarea)} title="Editar" type="button"><Pencil size={17} /></button><button className="icon-button danger" onClick={() => eliminar(tarea.id)} title="Eliminar" type="button"><Trash2 size={17} /></button></div>
              </article>
            ))}</div>
          }
        </section>
      </section>
      <footer>Gestor de tareas · Aplicación web con Spring Boot, React y PostgreSQL</footer>
    </main>
  )
}

export default App
