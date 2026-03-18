import { useEffect, useState } from 'react';
import { api } from '../services/api';

export default function TransactionHistory({ selectedCustomer }) {
  const [customers, setCustomers] = useState([]);
  const [customerId, setCustomerId] = useState('');
  const [transactions, setTransactions] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    api.getCustomers().then(setCustomers).catch(error => setMessage(error.message));
  }, []);

  useEffect(() => {
    if (selectedCustomer) {
      setCustomerId(String(selectedCustomer.id));
      loadTransactions(selectedCustomer.id);
    }
  }, [selectedCustomer]);

  async function loadTransactions(id = customerId) {
    if (!id) return;
    setMessage('');
    try {
      const data = await api.getTransactionsByCustomer(id);
      setTransactions(data);
      if (!data.length) setMessage('Este cliente aún no tiene transacciones.');
    } catch (error) {
      setMessage(error.message);
    }
  }

  return (
    <div className="panel">
      <div className="panel-header">
        <div>
          <h2>Historial de transacciones</h2>
          <p>Vista mínima para consultar el historial por cliente.</p>
        </div>
      </div>

      <div className="card">
        <div className="history-controls">
          <select value={customerId} onChange={e => setCustomerId(e.target.value)}>
            <option value="">Seleccione cliente...</option>
            {customers.map(c => (
              <option key={c.id} value={c.id}>{c.fullName}</option>
            ))}
          </select>
          <button className="secondary-btn" onClick={() => loadTransactions()}>Consultar</button>
        </div>

        {message && <div className="message">{message}</div>}

        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Origen</th>
                <th>Destino</th>
                <th>Monto</th>
                <th>Fecha</th>
                <th>Descripción</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map(tx => (
                <tr key={tx.id}>
                  <td>{tx.id}</td>
                  <td>{tx.sourceCustomerName}</td>
                  <td>{tx.targetCustomerName}</td>
                  <td>${Number(tx.amount).toLocaleString('es-CO')}</td>
                  <td>{new Date(tx.createdAt).toLocaleString('es-CO')}</td>
                  <td>{tx.description}</td>
                </tr>
              ))}
              {!transactions.length && (
                <tr><td colSpan="6">Sin datos para mostrar.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
