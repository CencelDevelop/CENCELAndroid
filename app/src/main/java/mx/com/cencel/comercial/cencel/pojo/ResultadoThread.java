package mx.com.cencel.comercial.cencel.pojo;

/**
 * Created by vcid on 08/09/15.
 */
public class ResultadoThread {

    public String saludo;
    public String puntos;
    public String cenceles;
    public String ImagenPun;
    public int puntos2;
    public String guid;

    public ResultadoThread(String saludo, String puntos, String cenceles, Integer puntos2, String guid) {
        this.saludo = saludo;
        this.puntos = puntos;
        this.cenceles = cenceles;
        this.puntos2 = puntos2;
        this.guid = guid;
    }

    public ResultadoThread() {
        super();
    }
}
