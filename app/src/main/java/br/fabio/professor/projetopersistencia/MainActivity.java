package br.fabio.professor.projetopersistencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.fabio.professor.dao.DaoAdapter;
import br.fabio.professor.dao.DaoUsuario;
import br.fabio.professor.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    private DaoUsuario daoUsuario;
    private EditText txtUsuario;
    private EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoAdapter daoAdapter = new DaoAdapter(this);
        daoAdapter.onCreate(daoAdapter.getWritableDatabase());

        daoUsuario = new DaoUsuario(this);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    public void entrar(View v){
        String usuario = txtUsuario.getText().toString();
        String senha = txtSenha.getText().toString();
        if(usuario.length() < 3){
            txtUsuario.setError("Usuário inválido!");
        } else if(senha.length() < 6){
            txtSenha.setError("Senha inválida!");
        } else {
            //devemos autenticar no banco de dados
            Usuario autenticar = new Usuario();
            autenticar.setUsuario(usuario);
            autenticar.setSenha(senha);
            autenticar = daoUsuario.autenticar(autenticar);
            if(autenticar == null){
                txtSenha.setText("");
                txtSenha.setError("Usuário ou senha inválida!");
            } else{
                Intent i = new Intent(this, InicioActivity.class);
                i.putExtra("idUser", autenticar.getId());
                startActivity(i);
            }
        }
    }

    public void cadastrar(View v){
        //abrir a activity de cadastro
        Intent i = new Intent(this, NovoUsuarioActivity.class);
        startActivity(i);
    }
}