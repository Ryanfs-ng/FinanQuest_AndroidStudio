package com.example.finanquest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class NovaTransacaoDialog extends DialogFragment {

    private boolean isUpdating = false; // controle da máscara de valor

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.DialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_nova_transacao, null);

        EditText etData = view.findViewById(R.id.etData);
        Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        EditText etBeneficiario = view.findViewById(R.id.etBeneficiario);
        EditText etValor = view.findViewById(R.id.etValor);
        EditText etNotas = view.findViewById(R.id.etNotas);
        Button btnCriar = view.findViewById(R.id.btnCriarTransacao);

        // Criação de Spinner para ter varias categorias de transações
        String[] categorias = {"Receita", "Compras Online", "Assinatura", "Comida", "Outros"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                requireContext(),
//                android.R.layout.simple_spinner_dropdown_item,
//                categorias
//        );
//        spinnerCategoria.setAdapter(adapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categorias
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE); // texto da seleção
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE); // texto do dropdown
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);


        //  Utilizando a ferramenta de dataPicker para seleção das datas
        etData.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        String dataSelecionada = String.format(Locale.getDefault(),
                                "%02d/%02d/%d", dayOfMonth, month + 1, year);
                        etData.setText(dataSelecionada);
                    }, ano, mes, dia);
            datePicker.show();
        });

        // Formatação do valor digitado e impede de deixa-lo vazio
        etValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                isUpdating = true;

                try {
                    // Remove tudo que não for número
                    String str = s.toString().replaceAll("[R$,.\\s]", "");
                    if (str.isEmpty()) {
                        etValor.setText("");
                        isUpdating = false;
                        return;
                    }

                    long valorLong = Long.parseLong(str);

                    // Pega o valor e divide por 100 para ajuste nas casas decimais
                    double valor = valorLong / 100.0;

                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    String valorFormatado = format.format(valor);

                    etValor.setText(valorFormatado);
                    etValor.setSelection(valorFormatado.length());

                } catch (Exception e) {
                    // evita crash caso o parse falhe
                    e.printStackTrace();
                    etValor.setText("");
                }

                isUpdating = false;
            }
        });

        // Botão para criação da transação e criando padrões caso campos estejam vazios
        btnCriar.setOnClickListener(v -> {
            String data = etData.getText().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            String beneficiario = etBeneficiario.getText().toString();
            String valor = etValor.getText().toString();
            String notas = etNotas.getText().toString();

            //Condição para saber se está entrando ou saindo dinheiro da conta
            if (!valor.isEmpty()) {
                if (categoria.equalsIgnoreCase("Receita") && !valor.startsWith("+")) {
                    valor = "+" + valor.replace("-", "");
                } else if (!categoria.equalsIgnoreCase("Receita") && !valor.startsWith("-")) {
                    valor = "-" + valor.replace("+", "");
                }
            }

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).adicionarTransacao(
                        beneficiario.isEmpty() ? "Nova Transação" : beneficiario,
                        categoria,
                        valor.isEmpty() ? "R$ 0,00" : valor,
                        data.isEmpty() ? "Hoje" : data
                );
            }

            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
