package br.fabio.professor.projetopersistencia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.fabio.professor.dao.DaoUsuario;
import br.fabio.professor.modelo.Usuario;

public class NovoUsuarioActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtUsuario;
    private EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    public void salvar(View v){
        String nome = txtNome.getText().toString();
        String usuario = txtUsuario.getText().toString();
        String senha = txtSenha.getText().toString();
        boolean check = true;

        if(nome.length() <= 3) {
            txtNome.setError("Digite o nome corretamente!");
            check = false;
        }
        if(usuario.length() <= 3){
            txtUsuario.setError("Digite o usuario corretamente!");
            check = false;
        }
        if(senha.length() < 6){
            txtSenha.setError("Minimo de 6 caracteres!");
            check = false;
        }

        if (check){
            Usuario novoUsuario = new Usuario(0l, nome, usuario, senha);
            DaoUsuario daoUsuario = new DaoUsuario(this);
            if(daoUsuario.insert(novoUsuario) > 0l){
                Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else{
                Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
