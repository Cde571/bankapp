import { useState } from 'react';
import CustomerList from './components/CustomerList';
import TransferForm from './components/TransferForm';
import TransactionHistory from './components/TransactionHistory';

export default function App() {
  const [view, setView] = useState('customers');
  const [selectedCustomer, setSelectedCustomer] = useState(null);

  function goToTransfer(customer) {
    setSelectedCustomer(customer);
    setView('transfer');
  }

  function goToHistory(customer) {
    setSelectedCustomer(customer);
    setView('history');
  }

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <h1>Banco2026v</h1>
        <p>Frontend + Backend funcional del laboratorio</p>
        <button className={view === 'customers' ? 'nav-btn active' : 'nav-btn'} onClick={() => setView('customers')}>
          Consultar clientes
        </button>
        <button className={view === 'transfer' ? 'nav-btn active' : 'nav-btn'} onClick={() => setView('transfer')}>
          Realizar transferencia
        </button>
        <button className={view === 'history' ? 'nav-btn active' : 'nav-btn'} onClick={() => setView('history')}>
          Historial por cliente
        </button>

        <div className="tip-box">
          <strong>Opcional incluido</strong>
          <span>Crear, actualizar y borrar clientes desde la vista principal.</span>
        </div>
      </aside>

      <main className="content">
        {view === 'customers' && <CustomerList onTransfer={goToTransfer} onHistory={goToHistory} />}
        {view === 'transfer' && <TransferForm selectedCustomer={selectedCustomer} />}
        {view === 'history' && <TransactionHistory selectedCustomer={selectedCustomer} />}
      </main>
    </div>
  );
}
