import { useEffect, useState } from 'react';
import { api } from '../services/api';

export default function TransferForm({ selectedCustomer }) {
  const [customers, setCustomers] = useState([]);
  const [form, setForm] = useState({
    sourceCustomerId: '',
    targetCustomerId: '',
    amount: '',
    description: ''
  });
  const [message, setMessage] = useState('');

  useEffect(() => {
    api.getCustomers().then(setCustomers).catch(error => setMessage(error.message));
  }, []);

  useEffect(() => {
    if (selectedCustomer) {
      setForm(prev => ({ ...prev, sourceCustomerId: selectedCustomer.id }));
    }
  }, [selectedCustomer]);

  async function handleSubmit(e) {
    e.preventDefault();
    setMessage('');
    try {
      await api.transfer({
        ...form,
        sourceCustomerId: Number(form.sourceCustomerId),
        targetCustomerId: Number(form.targetCustomerId),
        amount: Number(form.amount)
      });
      setMessage('Transferencia realizada con éxito');
      setForm(prev => ({ ...prev, amount: '', description: '' }));
    } catch (error) {
      setMessage(error.message);
    }
  }

  return (
    <div className="panel">
      <div className="panel-header">
        <div>
          <h2>Realizar transferencia</h2>
          <p>Formulario para enviar un TransferRequestDTO al backend.</p>
        </div>
      </div>

      {message && <div className="message">{message}</div>}

      <div className="card form-card">
        <form className="form" onSubmit={handleSubmit}>
          <label>Cuenta origen</label>
          <select value={form.sourceCustomerId} onChange={e => setForm({ ...form, sourceCustomerId: e.target.value })}>
            <option value="">Seleccione...</option>
            {customers.map(c => (
              <option key={c.id} value={c.id}>{c.fullName} · {c.accountNumber}</option>
            ))}
          </select>

          <label>Cuenta destino</label>
          <select value={form.targetCustomerId} onChange={e => setForm({ ...form, targetCustomerId: e.target.value })}>
            <option value="">Seleccione...</option>
            {customers.map(c => (
              <option key={c.id} value={c.id}>{c.fullName} · {c.accountNumber}</option>
            ))}
          </select>

          <label>Monto</label>
          <input type="number" step="0.01" placeholder="0" value={form.amount} onChange={e => setForm({ ...form, amount: e.target.value })} />

          <label>Descripción</label>
          <textarea placeholder="Motivo de la transferencia" value={form.description} onChange={e => setForm({ ...form, description: e.target.value })}></textarea>

          <button className="primary-btn" type="submit">Enviar transferencia</button>
        </form>
      </div>
    </div>
  );
}
