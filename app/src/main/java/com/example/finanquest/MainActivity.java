package com.example.finanquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Uso do recyclerView para exibir as transações dinamicamente
        recyclerView = findViewById(R.id.recyclerTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Criação da Lista de transações e referenciando como objetos
        transactions = new ArrayList<>();
        transactions.add(new Transaction("DROPBOX PRO", "Assinatura", "-R$ 144,00", "13 OUT 2025"));
        transactions.add(new Transaction("SPOTIFY PREMIUM", "Assinatura", "-R$ 24,00", "10 OUT 2025"));
        transactions.add(new Transaction("SALÁRIO", "Receita", "+R$ 1518,00", "05 OUT 2025"));
        transactions.add(new Transaction("AMAZON", "Compras Online", " -R$ 44,99", "25 SET 2025"));
        transactions.add(new Transaction("YOUTUBE ADS", "Receita", "+R$ 32,00", "24 SET 2025"));
        transactions.add(new Transaction("Compras do mercado", "Despesa", "-R$ 250,00", "10 SET 2025"));
        transactions.add(new Transaction("Imóveis", "Outros", "-R$ 400,00", "10 SET 2025"));

        //Adapter para deixa-lo mais dinamico
        adapter = new TransactionAdapter(transactions);
        recyclerView.setAdapter(adapter);

        //Botão sobrepondo a tela para nova despesa
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            NovaTransacaoDialog dialog = new NovaTransacaoDialog();
            dialog.show(getSupportFragmentManager(), "NovaTransacaoDialog");
        });

        //Navegação da Navbar inferior com uso de IDs (no momento so apresenta o nome com TOAST)
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Início", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_analytics) {
                Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
                intent.putExtra("transactions", new ArrayList<>(transactions)); // precisa ser Serializable
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (id == R.id.nav_goals) {
                Toast.makeText(this, "Metas", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_achievements) {
                Toast.makeText(this, "Conquistas", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }
        //Adiciona a transação criada na modal no topo da lista
    public void adicionarTransacao(String nome, String categoria, String valor, String data) {
        transactions.add(0, new Transaction(nome, categoria, valor, data));
        adapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }
}