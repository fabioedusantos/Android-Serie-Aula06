package br.fabio.professor.dao;


import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import br.fabio.professor.modelo.Conta;
import br.fabio.professor.modelo.Usuario;

public class DaoConta {
    private Context context;
    private DaoAdapter banco;

    public DaoConta(Context context) {
        this.context = context;
        //instanciamos o DaoAdapter (Dao mãe)
        banco = new DaoAdapter(context);
    }

    public long insert(Conta conta) {
        /*
         * Este método é um pouco mais trabalhado porém, nos retorna o id do
		 * utlimo registro. Este processo soluciona quando temos que inserir
		 * dados em chaves estrangeiras em outras tabelas...
		 */
        ContentValues values = new ContentValues();
        values.put("titulo", conta.getTitulo());
        values.put("valor", conta.getValor());
        values.put("idUsuario", conta.getUsuario().getId());

        long result = banco.queryInsertLastId("conta", values);

        return result;
    }

    //Método de alteração
    public boolean update(Conta conta) {
        Object[] args = {
                conta.getTitulo(),
                conta.getValor(),
                conta.getId()
        };

        boolean result = banco.queryExecute("UPDATE conta SET titulo = ?, valor = ? WHERE id = ?;", args);

        return result;
    }

    //Método de exclusão
    public boolean delete(long id) {
        Object[] args = {id};
        boolean result = banco.queryExecute("DELETE FROM conta WHERE id = ?", args);

        return result;
    }

    //Método de consulta geral
    public ArrayList<Conta> getTodos(long idUser) {
        String[] args = {String.valueOf(idUser)};
        ArrayList<Conta> contas = new ArrayList<Conta>();
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM conta WHERE idUsuario = ? ORDER BY valor DESC", args);

        if (ob != null && ob.size() > 0) {
            for (int i = 0; i < ob.size(); i++) {
                Conta conta = new Conta();
                Usuario usuario = new Usuario();
                conta.setId(ob.getLong(i, "id"));
                conta.setTitulo(ob.getString(i, "titulo"));
                conta.setValor(ob.getDouble(i, "valor"));
                usuario.setId(ob.getLong(i, "idUsuario"));
                conta.setUsuario(usuario);

                contas.add(conta);
            }
        }

        return contas;
    }

    //Método de consulta especifica
    public Conta getConta(long id) {
        String[] args = {String.valueOf(id)};
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM conta WHERE id = ?", args);

        Conta conta = null;
        if (ob != null && ob.size() > 0) {
            conta = new Conta();
            Usuario usuario = new Usuario();
            conta.setId(ob.getLong(0, "id"));
            conta.setTitulo(ob.getString(0, "titulo"));
            conta.setValor(ob.getDouble(0, "valor"));
            usuario.setId(ob.getLong(0, "idUsuario"));
            conta.setUsuario(usuario);
        }

        return conta;
    }
}