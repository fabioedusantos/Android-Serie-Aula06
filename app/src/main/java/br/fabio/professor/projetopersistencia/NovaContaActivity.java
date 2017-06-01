package br.fabio.professor.projetopersistencia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.fabio.professor.dao.DaoConta;
import br.fabio.professor.modelo.Conta;
import br.fabio.professor.modelo.Usuario;

public class NovaContaActivity extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtValor;
    private DaoConta daoConta;
    private long idUser = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta);

        daoConta = new DaoConta(this);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtValor = (EditText) findViewById(R.id.txtValor);

        Bundle b = getIntent().getExtras();
        if(b != null){
            idUser = b.getLong("idUser");
        }
    }

    public void salvar(View v){
        String titulo = txtTitulo.getText().toString();
        double valor = Double.parseDouble(txtValor.getText().toString());
        boolean check = true;

        if(titulo.length() <= 3){
            txtTitulo.setError("Digite o titulo corretamente!");
            check = false;
        }
        if(valor == 0){
            txtValor.setError("Digite o valor corretamente!");
            check = false;
        }

        if(check){
            Conta conta = new Conta(0l, titulo, valor, new Usuario(idUser));
            if(daoConta.insert(conta) > 0){
                Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else{
                Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
