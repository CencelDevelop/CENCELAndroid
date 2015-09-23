package mx.com.cencel.comercial.cencel.util;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLite {

    SQLiteHelper sqliteHelper;
    SQLiteDatabase db;

    /** Constructor de clase */
    public SQLite(Context context)
    {
        sqliteHelper = new SQLiteHelper( context );
    }

    /** Abre conexion a base de datos */
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteHelper.getDatabaseName() );
        db = sqliteHelper.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteHelper.getDatabaseName() );
        sqliteHelper.close();
    }

    /**
     * Metodo para obtener registros que correspondan solo a "guid" ordenados por nombre
     * @return Cursor
     * */
    public Cursor getGuid()
    {
        Log.i("SQLite", "query -> Consulta existencia de registros' " );
        //tabla
        //columnas ,
        //selection WHERE ,
        //selectionArgs , groupby, having,
        //orderby
        return db.query( sqliteHelper.__tabla__ ,
                new String[]{ sqliteHelper.__campo_guid },
                null, null, null, null, null);
    }

    /**
     * Metodo para agregar un nuevo registro
     * @param guid
     * @return -1 si ocurrio un error o ID de fila insertada
     * */
    public long insertarGuid( String guid)
    {
        Log.i("SQLite", "INSERT: "+guid);
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqliteHelper.__campo_guid, guid);
      ;
        //table, nullColumnHack, values
        return db.insert(sqliteHelper.__tabla__ , null, contentValues );
    }

    /**
     * Metodo para eliminar un registro
    // * @param id Identificador unico de registro PRIMARY KEY
     * @return El número de filas afectadas 0 en caso contrario.
     * */

    //public int eliminarGuid( int id )
    //{
      //  Log.i("SQLite", "DELETE: id=" + id );
        //table , whereClause, whereArgs
        //return db.delete( sqliteHelper.__tabla__ , sqliteHelper.__campo_id + " = " + id ,  null);
    //}

    public int eliminarGuid(  )
    {
      Log.i("SQLite", "DELETE:" );
    //table , whereClause, whereArgs
     return db.delete(sqliteHelper.__tabla__ , null , null);
    }

    /**
     * Metodo para actualizar un registro
     * @param id Identificador unico de registro PRIMARY KEY
     * @param guid

     * @return numero de filas afectadas
     * */
    public int actualizarGuid( int id, String guid  )
    {
        Log.i("SQLite", "UPDATE: id=" + id + " - " + guid);
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqliteHelper.__campo_guid , guid);
        return db.update(sqliteHelper.__tabla__ , contentValues, sqliteHelper.__campo_id + " = " + id , null);
    }

    /**
     * Metodo que dado un Cursor, recorre los registros y coloca en un String
     * @param Cursor
     * @return String registros separados por comas y salto de linea
     * */
    public String imprimirListaguid(Cursor cursor)
    {
        StringBuffer lista = new StringBuffer();
        if(cursor.moveToFirst())
        {
            do{
                //lista.append( cursor.getString( 0 ) + "," );
                lista.append(cursor.getString(0));
                //lista.append( cursor.getString( 2 ) + "\r\n" );
            }while( cursor.moveToNext() );
        }
        return lista.toString();
    }

}