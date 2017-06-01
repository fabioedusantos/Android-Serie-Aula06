package br.fabio.professor.dao;


import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import br.fabio.professor.modelo.Usuario;

public class DaoUsuario {
    private Context context;
    private DaoAdapter banco;

    public DaoUsuario(Context context) {
        this.context = context;
        //instanciamos o DaoAdapter (Dao mãe)
        banco = new DaoAdapter(context);
    }

    public long insert(Usuario usuario) {
        /*
         * Este método é um pouco mais trabalhado porém, nos retorna o id do
		 * utlimo registro. Este processo soluciona quando temos que inserir
		 * dados em chaves estrangeiras em outras tabelas...
		 */
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("usuario", usuario.getUsuario());
        values.put("senha", usuario.getSenha());

        long result = banco.queryInsertLastId("usuario", values);

        return result;
    }

    //Método de alteração
    public boolean update(Usuario usuario) {
        Object[] args = {
                usuario.getNome(),
                usuario.getUsuario(),
                usuario.getSenha(),
                usuario.getId()
        };

        boolean result = banco.queryExecute("UPDATE usuario SET nome = ?, usuario = ?, senha = ? WHERE id = ?;", args);

        return result;
    }

    //Método de exclusão
    public boolean delete(long id) {
        Object[] args = {id};

        boolean result = false;
        if(banco.queryExecute("DELETE FROM conta WHERE idUsuario = ?", args)) {
            result = banco.queryExecute("DELETE FROM usuario WHERE id = ?", args);
        }

        return result;
    }

    //Método de consulta geral
    public ArrayList<Usuario> getTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM usuario ORDER BY nome ASC", null);

        if (ob != null && ob.size() > 0) {
            for (int i = 0; i < ob.size(); i++) {
                Usuario usuario = new Usuario();
                usuario.setId(ob.getLong(i, "id"));
                usuario.setNome(ob.getString(i, "nome"));
                usuario.setUsuario(ob.getString(i, "usuario"));
                usuario.setSenha(ob.getString(i, "senha"));

                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    //Método de consulta especifica
    public Usuario getUsuario(long id) {
        String[] args = {String.valueOf(id)};
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM usuario WHERE id = ?", args);

        Usuario usuario = null;
        if (ob != null && ob.size() > 0) {
            usuario = new Usuario();
            usuario.setId(ob.getLong(0, "id"));
            usuario.setNome(ob.getString(0, "nome"));
            usuario.setUsuario(ob.getString(0, "usuario"));
            usuario.setSenha(ob.getString(0, "senha"));
        }

        return usuario;
    }

    public Usuario autenticar(Usuario usuario) {
        String[] args = {
                usuario.getUsuario(),
                usuario.getSenha()
        };
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM usuario WHERE usuario = ? AND senha = ?", args);

        usuario = null;
        if (ob != null && ob.size() > 0) {
            usuario = new Usuario();
            usuario.setId(ob.getLong(0, "id"));
            usuario.setNome(ob.getString(0, "nome"));
            usuario.setUsuario(ob.getString(0, "usuario"));
            usuario.setSenha(ob.getString(0, "senha"));
        }

        return usuario;
    }
}