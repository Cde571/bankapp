const API_URL = 'http://localhost:8080/api';

async function request(path, options = {}) {
  const response = await fetch(`${API_URL}${path}`, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options,
  });

  if (!response.ok) {
    let message = 'Error inesperado';
    try {
      const data = await response.json();
      message = data.message || message;
    } catch (_) {}
    throw new Error(message);
  }

  if (response.status === 204) return null;
  return response.json();
}

export const api = {
  getCustomers: () => request('/customers'),
  createCustomer: (customer) => request('/customers', { method: 'POST', body: JSON.stringify(customer) }),
  updateCustomer: (id, customer) => request(`/customers/${id}`, { method: 'PUT', body: JSON.stringify(customer) }),
  deleteCustomer: (id) => request(`/customers/${id}`, { method: 'DELETE' }),
  transfer: (payload) => request('/transactions/transfer', { method: 'POST', body: JSON.stringify(payload) }),
  getTransactionsByCustomer: (customerId) => request(`/transactions/customer/${customerId}`)
};
