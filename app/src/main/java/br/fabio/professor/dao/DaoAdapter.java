package br.fabio.professor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DaoAdapter extends SQLiteOpenHelper {

    private final String LOGTAG = "teste";

    //nome do banco
    public static final String BANCO = "NOME_DO_BANCO";
    /*
     * Versão do banco, utilizamos quando fazemos o controle
     * de versão do banco de dados...
     */
    public static final int VERSAO = 1;

    //Query de exlusão de todas as tabelas
    private static final String queryDelete[] = {
            "DROP TABLE IF EXISTS usuario;",
            "DROP TABLE IF EXISTS conta;",
    };
    //Query de criação de todas as tabelas
    private static final String query[] = {
            "CREATE TABLE IF NOT EXISTS usuario(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "nome VARCHAR(30) NOT NULL," +
                    "usuario VARCHAR(20) NOT NULL," +
                    "senha VARCHAR(20) NOT NULL" +
                    ");",
            "CREATE TABLE IF NOT EXISTS conta(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "titulo VARCHAR(50) NOT NULL," +
                    "valor DOUBLE(10,2) NOT NULL," +
                    "idUsuario INTEGER NOT NULL," +
                    "FOREIGN KEY(idUsuario) REFERENCES usuario(id)" +
                    ");"
    };

    //Construtor DaoAdapter
    public DaoAdapter(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    //Método de criação do banco (gera um novo banco)
    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < query.length; i++) db.execSQL(query[i]);
        //close();
    }

    //Método de reset para o banco (apaga e gera um novo banco)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = 0; i < queryDelete.length; i++) db.execSQL(queryDelete[i]);	//limpamos
        onCreate(db);															//criamos
        //close();
    }

    /*
     * Método responsável pela execução de querys básicas
     * como insert, update e delete...
     */
    public boolean queryExecute(String query, Object[] args){
        /*
		 * String query => recebe uma query (INSERT INTO...)
		 * Object[] args => recebe os argumentos da query, exemplo:
		 *   INSERT INTO tabela (nome) VALUES (?)
		 * teriamos como argumentos o substituto do ?, então,
		 * poderiamos enviar "Fábio" argumento, ou seja, o primeiro
		 * indice do vetor args seria "Fábio"...
		 * EVITA SQL INJECTION!
		 */
        boolean status = true;
        try {
            getWritableDatabase().execSQL(query, args);
        } catch(SQLException e){
            Log.e(LOGTAG, "DaoAdapter queryExecute: " + e.getMessage());
            status = false;
        }
        close();

        return status;
    }

    /*
     * Método responsável pela execução de querys INSERT.
     * Ao final, se sucesso, retorna o ID do registro inserido
     * no banco...
     */
    public long queryInsertLastId(String table, ContentValues values){
		/*
		 * table => nome da tabela
		 * values => valores da tabela (nome, telefone, rg...)
		 */
        long status = -1;
        try {
            status = getWritableDatabase().insert(table, null, values);
        } catch(SQLException e){
            Log.e(LOGTAG, "DaoAdapter queryInsertLastId: " + e.getMessage());
        }
        close();

        return status;
    }

    /*
     * Método responsável pela execução de querys SELECT no banco
     */
    public ObjetoBanco queryConsulta(String query, String[] args){
		/*
		 * query => SELECT * FROM tabela WHERE id = ?
		 * args => mesma explicação do método queryExecute
		 * exemplo: 5, então ? seria substituido pelo numero 5
		 * se estivesse contido no primeiro indice de args
		 */
        Cursor c = null;
        try {
            c = getReadableDatabase().rawQuery(query, args);
        } catch(SQLException e){
            Log.e(LOGTAG, "DaoAdapter queryConsulta: " + e.getMessage());
        }

        ObjetoBanco ob = null;

        if(c != null){
            ob = new ObjetoBanco();
            ob.setDados(c);
        }
        if(!c.isClosed()) c.close();
        close();

        return ob;
    }

}