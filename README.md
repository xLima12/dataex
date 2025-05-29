# 📦 DataEx - Leitor e Gerador de ASN/XML

Este projeto Java desktop permite a leitura de arquivos de notas fiscais nos formatos **CSV**, **XLS** e **XLSX**, realizando validações e gerando arquivos **ASN (Advanced Shipping Notice)** no formato **XML**.

## 🎯 Objetivo

Automatizar o processo de importação de notas fiscais a partir de diferentes formatos, validando as informações e convertendo-as para um formato padronizado, pronto para integração.

---

## 🚀 Funcionalidades

* Leitura de arquivos nos formatos: `.csv`, `.xls`, `.xlsx`
* Validação de dados essenciais (campos obrigatórios, filial de destino, etc.)
* Tratamento e exibição de erros via `JOptionPane`
* Geração de arquivos ASN no formato XML

---

## 🏗️ Estrutura de Pastas

```
src/
├── App.java
├── controllers/
│   └── ImportFileController.java
├── domain/
│   ├── factory/
│   │   └── InvoiceReaderFactory.java
│   ├── interfaces/
│   │   └── InvoiceReader.java
│   ├── readers/
│   │   ├── ReadFileCSV.java
│   │   ├── ReadFileXLS.java
│   │   └── ReadFileXLSX.java
│   └── services/
│       ├── CreateAsnDetail.java
│       └── CreateXML.java
├── exceptions/
│   ├── ExceptionHandler.java
│   ├── FileEmptyException.java
│   ├── FileReadException.java
│   ├── InvalidBranchDestinationException.java
│   ├── MissingOrInvalidHeaderException.java
│   └── UnsupportedFileEncodingException.java
├── model/
│   └── Invoice.java
└── view/
    └── WindowImportFile.java
```

---

## 🧪 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/invoice-importer.git
```

2. Compile o projeto:

```bash
javac -d bin src/**/*.java
```

3. Execute:

```bash
java -cp bin App
```

---

## 📚 Dependências

* Apache POI (para leitura de arquivos Excel)

---

## ⚠️ Possíveis Erros Tratados

* `FileEmptyException`: O arquivo está vazio.
* `FileReadException`: Nenhuma codificação suportada conseguiu ler o arquivo.
* `MissingOrInvalidHeaderException`: Cabeçalhos obrigatórios ausentes ou mal formatados.
* `InvalidBranchDestinationException`: As linhas do arquivo possuem filiais de destino diferentes.
* `UnsupportedFileEncodingException`: Codificação inválida ao ler o arquivo.

Todos esses erros são exibidos para o usuário via `JOptionPane`.

---

## 👨‍💻 Autor

Desenvolvido por Felipe Lima 🧠

---
