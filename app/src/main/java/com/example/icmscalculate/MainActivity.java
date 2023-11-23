package com.example.icmscalculate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private Button calcular;    // Botão de cálculo
    private TextView textView;  // Campo de exibição do resultado
    private EditText editText1; // Campo de entrada para o valor
    private EditText editText2; // Campo de entrada para a taxa de ICMS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização dos componentes da interface do usuário
        calcular = findViewById(R.id.button1);
        textView = findViewById(R.id.textView1);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        // Configuração do evento de clique do botão
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Obtenção dos valores inseridos nos campos de texto
                    String value = editText1.getText().toString();
                    String icms = editText2.getText().toString();

                    // Converte os valores para números
                    double valueDouble = parseNumericValue(value);
                    double icmsDouble = parseNumericValue(icms);

                    // Verificação da validade dos valores inseridos
                    if (!Double.isNaN(valueDouble) && !Double.isNaN(icmsDouble)) {
                        // Exibição do resultado do cálculo do ICMS
                        textView.setText(calculateAndDisplayIcms(valueDouble, icmsDouble));
                    } else {
                        // Exibição de mensagem de erro em caso de entrada inválida
                        Toast.makeText(MainActivity.this, "Entrada inválida", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Tratamento de exceção se a conversão para número falhar
                    Toast.makeText(MainActivity.this, "Erro ao converter para número", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Verifica se os campos de entrada são válidos
    private boolean isValidInput(String value, String icms) {
        return !TextUtils.isEmpty(value) && !TextUtils.isEmpty(icms);
    }

    // Converte a entrada para um valor numérico
    private double parseNumericValue(String input) {
        try {
            // Remove caracteres não numéricos e faz a conversão
            String cleanedInput = input.replaceAll("[^\\d.,]", "");
            return NumberFormat.getInstance().parse(cleanedInput).doubleValue();
        } catch (ParseException e) {
            return Double.NaN; // Retorna NaN se a conversão falhar
        }
    }

    // Calcula o ICMS e exibe o resultado formatado
    private String calculateAndDisplayIcms(double value, double icms) {
        // O(1) - cálculo do valor do ICMS (operação constante)
        double calculate = (value * icms) / 100;
        double total = value + calculate;

        String calculateFormatted;
        String totalFormatted;

        // Formatação para exibir apenas duas casas decimais
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Verifica se o número é maior que 1000
        if (total > 1000) {
            // Formatação para exibir milhares separados por ponto
            DecimalFormat thousandsFormat = new DecimalFormat("#,###");
            calculateFormatted = thousandsFormat.format(calculate);
            totalFormatted = thousandsFormat.format(total);
        } else {
            // Formatação para exibir apenas duas casas decimais
            calculateFormatted = decimalFormat.format(calculate);
            totalFormatted = decimalFormat.format(total);
        }

        // Limpa os campos de texto
        editText1.setText("");
        editText2.setText("");

        // Retorna o resultado formatado
        return "ICMS: " + calculateFormatted + " $" + "\n\n TOTAL: " + totalFormatted + " $";
    }

}
