const API_URL = 'http://localhost:8080/clientes';
let currentClienteCpf = '';

document.addEventListener('DOMContentLoaded', function() {
    carregarClientes();
    
    document.getElementById('clienteForm').addEventListener('submit', function(e) {
        e.preventDefault();
        criarCliente();
    });

    document.getElementById('editClienteForm').addEventListener('submit', function(e) {
        e.preventDefault();
        atualizarCliente();
    });

    document.getElementById('novoEmpregoForm').addEventListener('submit', function(e) {
        e.preventDefault();
        adicionarEmprego();
    });
});

function openTab(evt, tabName) {
    const tabcontent = document.getElementsByClassName("tabcontent");
    for (let i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    
    const tablinks = document.getElementsByClassName("tablinks");
    for (let i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

async function carregarClientes() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error('Erro ao carregar clientes');
        }
        const clientes = await response.json();
        const tbody = document.getElementById('clientesBody');
        tbody.innerHTML = '';
        
        clientes.forEach(cliente => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${cliente.cpf}</td>
                <td>${cliente.rg || ''}</td>
                <td>${cliente.endereco || ''}</td>
                <td>${cliente.empregos ? cliente.empregos.length : 0}</td>
                <td>
                    <button onclick="abrirEdicao('${cliente.cpf}')">Editar</button>
                    <button onclick="excluirCliente('${cliente.cpf}')">Excluir</button>
                    <button onclick="verEmpregos('${cliente.cpf}')">Empregos</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        console.error('Erro ao carregar clientes:', error);
        alert('Erro ao carregar clientes');
    }
}

function adicionarEmpregoForm() {
    const container = document.getElementById('empregosContainer');
    const div = document.createElement('div');
    div.className = 'emprego-form';
    div.innerHTML = `
        <div class="form-group">
            <label>Empregador:</label>
            <input type="text" class="empregador" required>
        </div>
        <div class="form-group">
            <label>Salário:</label>
            <input type="number" step="0.01" class="salario" required>
        </div>
        <button type="button" onclick="this.parentNode.remove()">Remover</button>
    `;
    container.appendChild(div);
}

async function criarCliente() {
    const cpf = document.getElementById('cpf').value;
    const rg = document.getElementById('rg').value;
    const endereco = document.getElementById('endereco').value;
    
    const empregos = [];
    const empregoForms = document.querySelectorAll('.emprego-form');
    empregoForms.forEach(form => {
        empregos.push({
            empregador: form.querySelector('.empregador').value,
            salario: parseFloat(form.querySelector('.salario').value)
        });
    });
    
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                cpf,
                rg,
                endereco,
                empregos
            })
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Falha ao criar cliente');
        }
        
        alert('Cliente criado com sucesso!');
        document.getElementById('clienteForm').reset();
        document.getElementById('empregosContainer').innerHTML = '';
        carregarClientes();
        openTab(event, 'clientes');
    } catch (error) {
        console.error('Erro ao criar cliente:', error);
        alert(`Erro: ${error.message}`);
    }
}

async function abrirEdicao(cpf) {
    try {
        const response = await fetch(`${API_URL}/${cpf}`);
        if (!response.ok) {
            throw new Error('Cliente não encontrado');
        }
        const cliente = await response.json();
        
        document.getElementById('editCpf').value = cliente.cpf;
        document.getElementById('editRg').value = cliente.rg || '';
        document.getElementById('editEndereco').value = cliente.endereco || '';
        document.getElementById('editModal').style.display = 'block';
    } catch (error) {
        console.error('Erro ao buscar cliente:', error);
        alert(`Erro: ${error.message}`);
    }
}

function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

async function atualizarCliente() {
    const cpf = document.getElementById('editCpf').value;
    const rg = document.getElementById('editRg').value;
    const endereco = document.getElementById('editEndereco').value;
    
    try {
        const response = await fetch(`${API_URL}/${cpf}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                rg,
                endereco
            })
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Falha ao atualizar cliente');
        }
        
        alert('Cliente atualizado com sucesso!');
        closeModal();
        carregarClientes();
    } catch (error) {
        console.error('Erro ao atualizar cliente:', error);
        alert(`Erro: ${error.message}`);
    }
}

async function excluirCliente(cpf) {
    if (!confirm('Tem certeza que deseja excluir este cliente?')) return;
    
    try {
        const response = await fetch(`${API_URL}/${cpf}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Falha ao excluir cliente');
        }
        
        alert('Cliente excluído com sucesso!');
        carregarClientes();
    } catch (error) {
        console.error('Erro ao excluir cliente:', error);
        alert(`Erro: ${error.message}`);
    }
}

async function verEmpregos(cpf) {
    currentClienteCpf = cpf;
    document.getElementById('clienteCpf').value = cpf;
    
    try {
        const response = await fetch(`${API_URL}/${cpf}/empregos`);
        if (!response.ok) {
            throw new Error('Erro ao carregar empregos');
        }
        const empregos = await response.json();
        const container = document.getElementById('empregosList');
        container.innerHTML = '';
        
        if (empregos.length === 0) {
            container.innerHTML = '<p>Nenhum emprego cadastrado</p>';
        } else {
            empregos.forEach(emprego => {
                const div = document.createElement('div');
                div.className = 'emprego-item';
                div.innerHTML = `
                    <div>
                        <strong>${emprego.empregador}</strong> - R$ ${emprego.salario.toFixed(2)}
                    </div>
                    <button onclick="removerEmprego('${emprego.id}')">Remover</button>
                `;
                container.appendChild(div);
            });
        }
        
        document.getElementById('empregosModal').style.display = 'block';
    } catch (error) {
        console.error('Erro ao carregar empregos:', error);
        alert(`Erro: ${error.message}`);
    }
}

function closeEmpregosModal() {
    document.getElementById('empregosModal').style.display = 'none';
}

async function adicionarEmprego() {
    const empregador = document.getElementById('empregador').value;
    const salario = parseFloat(document.getElementById('salario').value);
    const cpf = document.getElementById('clienteCpf').value;
    
    try {
        const response = await fetch(`${API_URL}/${cpf}/empregos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                empregador,
                salario
            })
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Falha ao adicionar emprego');
        }
        
        document.getElementById('empregador').value = '';
        document.getElementById('salario').value = '';
        verEmpregos(cpf);
    } catch (error) {
        console.error('Erro ao adicionar emprego:', error);
        alert(`Erro: ${error.message}`);
    }
}

async function removerEmprego(empregoId) {
    if (!confirm('Tem certeza que deseja remover este emprego?')) return;
    
    try {
        const response = await fetch(`${API_URL}/${currentClienteCpf}/empregos/${empregoId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Falha ao remover emprego');
        }
        
        verEmpregos(currentClienteCpf);
    } catch (error) {
        console.error('Erro ao remover emprego:', error);
        alert(`Erro: ${error.message}`);
    }
}