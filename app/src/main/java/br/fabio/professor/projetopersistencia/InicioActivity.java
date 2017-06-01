package br.fabio.professor.projetopersistencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.fabio.professor.dao.DaoConta;
import br.fabio.professor.modelo.Conta;

public class InicioActivity extends AppCompatActivity {

    private long idUser = 0l;
    private static final int NOVA_CONTA = 2222;
    private ListView lista;
    private DaoConta daoConta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        lista = (ListView) findViewById(R.id.lista);
        daoConta = new DaoConta(this);

        Bundle b = getIntent().getExtras();
        if(b != null){
            idUser = b.getLong("idUser");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> values = new ArrayList<String>();
        final ArrayList<Conta> contas = daoConta.getTodos(idUser);

        for(Conta c : contas){
            values.add("R$ " + c.getValor() + " " + c.getTitulo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lista.setAdapter(adapter);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Conta contaSelecionada = contas.get(position);
                daoConta.delete(contaSelecionada.getId());
                onResume();
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, NOVA_CONTA, 0, "Nova Conta");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case NOVA_CONTA:
                Intent i = new Intent(this, NovaContaActivity.class);
                i.putExtra("idUser", idUser);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}