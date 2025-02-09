# Agendamento de Consultas

## Descrição
Este sistema permite o agendamento de consultas médicas, facilitando a gestão de atendimentos. Ele foi desenvolvido utilizando **Java** e conta com **interface gráfica**.

## Funcionalidades
- 📌 **Cadastro de pacientes e médicos**
- 📅 **Agendamento de consultas**
- 🕒 **Gerenciamento de horários**
- 💾 **Persistência de dados utilizando DAO**
- 🖥 **Interface gráfica para facilitar a interação**

## Estrutura do Projeto
📂 `dao/` - Classes de acesso a banco de dados  
📂 `formularios/` - Interfaces gráficas do sistema  
📂 `imagens/` - Recursos visuais utilizados no projeto  
📂 `mapeamento/` - Mapeamento de entidades  
📂 `pesquisas/` - Lógicas de busca e consultas  
📂 `utilitario/` - Classes auxiliares e funções utilitárias  

## Requisitos
- ☕ **Java instalado**
- 🛠 **NetBeans** (recomendado para edição)
- 🗄 **Banco de dados configurado** (se aplicável)

## Configuração do Banco de Dados
```sql
CREATE DATABASE consultorio;

USE consultorio;

CREATE TABLE paciente (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100),
    datanasc VARCHAR(50),
    telefone VARCHAR(50),
    sexo VARCHAR(50)
);

CREATE TABLE medico (
    id_medico INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    crm VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    especializacao VARCHAR(50) NOT NULL
);

CREATE TABLE funcionario (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    dataadmissao VARCHAR(15) NOT NULL,
    senha VARCHAR(250) NOT NULL 
);

CREATE TABLE consulta (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    dataAtendimento VARCHAR(20) NOT NULL,
    horario VARCHAR(20) NOT NULL,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    id_funcionario INT NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    FOREIGN KEY (id_medico) REFERENCES medico(id_medico),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario)
);

SELECT * FROM paciente;
SELECT * FROM medico ORDER BY nome;
SELECT * FROM funcionario;
SELECT * FROM consulta;
SELECT * FROM medico;

DROP TABLE paciente;
DROP TABLE medico;
DROP TABLE funcionario;
DROP TABLE consulta;
