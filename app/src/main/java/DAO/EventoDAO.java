package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import POJO.Eventos;

public class EventoDAO {

    private SQLiteDatabase database;
    private BaseDAO dbHelper;

    //Campos da tabela Agenda
    private String[] colunas = {BaseDAO.EVENTO_ID,
            BaseDAO.EVENTO_NOMBRE,
            BaseDAO.EVENTO_LUGAR,
            BaseDAO.EVENTO_FECHA };

    public EventoDAO(Context context) {
        dbHelper = new BaseDAO(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long Inserir(Eventos pValue) {
        ContentValues values = new ContentValues();

        //Carregar os valores nos campos do Contato que será incluído
        values.put(BaseDAO.EVENTO_NOMBRE, pValue.getNombre());
        values.put(BaseDAO.EVENTO_LUGAR, pValue.getLugar());
        values.put(BaseDAO.EVENTO_FECHA, pValue.getFecha());

        return database.insert(BaseDAO.TABLA_EVENTOS, null, values);
    }


    public int Alterar(Eventos pValue) {
        long id = pValue.getId();
        ContentValues values = new ContentValues();

        //Carregar os novos valores nos campos que serão alterados
        values.put(BaseDAO.EVENTO_NOMBRE, pValue.getNombre());
        values.put(BaseDAO.EVENTO_LUGAR, pValue.getLugar());
        values.put(BaseDAO.EVENTO_FECHA, pValue.getFecha());

        //Alterar o registro com base no ID
        return database.update(BaseDAO.TABLA_EVENTOS, values, BaseDAO.EVENTO_ID + " = " + id, null);
    }

    public void Excluir(Eventos pValue) {
        long id = pValue.getId();

        //Exclui o registro com base no ID
        database.delete(BaseDAO.TABLA_EVENTOS, BaseDAO.EVENTO_ID + " = " + id, null);
    }

    public List<Eventos> Consultar() {
        List<Eventos> lstEvento = new ArrayList<Eventos>();

        //Consulta para trazer todos os dados da tabela Agenda ordenados pela coluna Nome
        Cursor cursor = database.query(BaseDAO.TABLA_EVENTOS, colunas,
                null, null, null, null, BaseDAO.EVENTO_NOMBRE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Eventos lEventos = cursorToEvento(cursor);
            lstEvento.add(lEventos);
            cursor.moveToNext();
        }

        //Tenha certeza que você fechou o cursor
        cursor.close();
        return lstEvento;
    }

    //Converter o Cursor de dados no objeto POJO ContatoVO
    private Eventos cursorToEvento(Cursor cursor) {
        Eventos lContatoVO = new Eventos();
        lContatoVO.setId(cursor.getLong(0));
        lContatoVO.setNombre(cursor.getString(1));
        lContatoVO.setLugar(cursor.getString(2));
        lContatoVO.setFecha(cursor.getString(3));
        return lContatoVO;
    }
}