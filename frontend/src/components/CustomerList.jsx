import { useEffect, useState } from 'react';
import { api } from '../services/api';

const initialForm = { fullName: '', accountNumber: '', balance: '', email: '' };

export default function CustomerList({ onTransfer, onHistory }) {
  const [customers, setCustomers] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState(null);
  const [message, setMessage] = useState('');

  async function loadCustomers() {
    try {
      const data = await api.getCustomers();
      setCustomers(data);
    } catch (error) {
      setMessage(error.message);
    }
  }

  useEffect(() => {
    loadCustomers();
  }, []);

  function fillForm(customer) {
    setEditingId(customer.id);
    setForm({
      fullName: customer.fullName,
      accountNumber: customer.accountNumber,
      balance: customer.balance,
      email: customer.email
    });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setMessage('');
    try {
      const payload = { ...form, balance: Number(form.balance) };
      if (editingId) {
        await api.updateCustomer(editingId, payload);
        setMessage('Cliente actualizado');
      } else {
        await api.createCustomer(payload);
        setMessage('Cliente creado');
      }
      setForm(initialForm);
      setEditingId(null);
      loadCustomers();
    } catch (error) {
      setMessage(error.message);
    }
  }

  async function handleDelete(id) {
    if (!confirm('¿Eliminar cliente?')) return;
    try {
      await api.deleteCustomer(id);
      setMessage('Cliente eliminado');
      loadCustomers();
    } catch (error) {
      setMessage(error.message);
    }
  }

  return (
    <div className="panel">
      <div className="panel-header">
        <div>
          <h2>Consultar clientes</h2>
          <p>Vista mínima del laboratorio para listar clientes del backend.</p>
        </div>
        <button className="secondary-btn" onClick={loadCustomers}>Recargar</button>
      </div>

      {message && <div className="message">{message}</div>}

      <div className="grid-two">
        <div className="card">
          <h3>{editingId ? 'Editar cliente' : 'Crear cliente'}</h3>
          <form className="form" onSubmit={handleSubmit}>
            <input placeholder="Nombre completo" value={form.fullName} onChange={e => setForm({ ...form, fullName: e.target.value })} />
            <input placeholder="Número de cuenta" value={form.accountNumber} onChange={e => setForm({ ...form, accountNumber: e.target.value })} />
            <input placeholder="Saldo inicial" type="number" step="0.01" value={form.balance} onChange={e => setForm({ ...form, balance: e.target.value })} />
            <input placeholder="Correo" type="email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} />
            <div className="row-actions">
              <button className="primary-btn" type="submit">{editingId ? 'Guardar cambios' : 'Crear cliente'}</button>
              {editingId && (
                <button className="secondary-btn" type="button" onClick={() => { setEditingId(null); setForm(initialForm); }}>
                  Cancelar
                </button>
              )}
            </div>
          </form>
        </div>

        <div className="card">
          <h3>Listado</h3>
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Cliente</th>
                  <th>Cuenta</th>
                  <th>Saldo</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {customers.map(customer => (
                  <tr key={customer.id}>
                    <td>{customer.id}</td>
                    <td>{customer.fullName}</td>
                    <td>{customer.accountNumber}</td>
                    <td>${Number(customer.balance).toLocaleString('es-CO')}</td>
                    <td>
                      <div className="actions">
                        <button className="tiny-btn" onClick={() => onHistory(customer)}>Historial</button>
                        <button className="tiny-btn" onClick={() => onTransfer(customer)}>Transferir</button>
                        <button className="tiny-btn" onClick={() => fillForm(customer)}>Editar</button>
                        <button className="tiny-btn danger" onClick={() => handleDelete(customer.id)}>Borrar</button>
                      </div>
                    </td>
                  </tr>
                ))}
                {!customers.length && (
                  <tr><td colSpan="5">No hay clientes cargados.</td></tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}
