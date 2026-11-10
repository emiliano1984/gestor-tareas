const API_URL = 'http://localhost:8080/api/tareas'

async function request(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options
  })

  if (!response.ok) {
    const error = await response.json().catch(() => ({}))
    throw new Error(error.error || 'No fue posible completar la operación')
  }

  if (response.status === 204) return null
  return response.json()
}

export const tareasApi = {
  listar: (filtros = {}) => {
    const params = new URLSearchParams()
    if (filtros.estado) params.set('estado', filtros.estado)
    if (filtros.prioridad) params.set('prioridad', filtros.prioridad)
    const query = params.toString()
    return request(`${API_URL}${query ? `?${query}` : ''}`)
  },
  crear: (tarea) => request(API_URL, {
    method: 'POST',
    body: JSON.stringify(tarea)
  }),
  actualizar: (id, tarea) => request(`${API_URL}/${id}`, {
    method: 'PUT',
    body: JSON.stringify(tarea)
  }),
  eliminar: (id) => request(`${API_URL}/${id}`, { method: 'DELETE' })
}
