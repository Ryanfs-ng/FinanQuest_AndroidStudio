// Crie este novo arquivo: DataManager.java
package com.example.finanquest;

import java.util.ArrayList;
import java.util.List;

// Usamos o padrão Singleton para garantir que só exista UMA lista de transações
// em todo o aplicativo.
public class DataManager {

    // A instância única do DataManager
    private static final DataManager instance = new DataManager();

    // A lista de transações que será compartilhada por todo o app
    public final List<Transaction> transactions = new ArrayList<>();

    // O construtor é privado para que ninguém mais possa criar uma nova instância
    private DataManager() {
        // Podemos carregar dados iniciais aqui, se quisermos.
        // Isto só será executado uma vez na vida do aplicativo.
        loadInitialData();
    }

    // O método público para obter a única instância existente
    public static DataManager getInstance() {
        return instance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Método para adicionar uma nova transação à lista central
    public void addTransaction(Transaction transaction) {
        transactions.add(0, transaction); // Adiciona no topo da lista
    }

    // Método privado para carregar dados de exemplo apenas uma vez
    private void loadInitialData() {
        transactions.add(new Transaction("Salário", "Receita", "5000.0", "25 OUT 2025"));
        transactions.add(new Transaction("Aluguel", "Despesa", "1500.0", "25 OUT 2025"));
        transactions.add(new Transaction("Supermercado", "Despesa", "400.0", "25 OUT 2025"));
    }
}
