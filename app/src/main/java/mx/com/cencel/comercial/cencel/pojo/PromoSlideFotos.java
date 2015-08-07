package mx.com.cencel.comercial.cencel.pojo;

import java.io.Serializable;

/**
 * Created by iHouse on 06/08/15.
 */
public class PromoSlideFotos implements Serializable {
    private String descripcionPromo;
    private boolean status;
    private String fotoActual;
    private int idFoto;

    public PromoSlideFotos(String descripcionPromo, boolean status, String fotoActual, int idFoto) {
        this.descripcionPromo = descripcionPromo;
        this.status = status;
        this.fotoActual = fotoActual;
        this.idFoto = idFoto;
    }

    public String getDescripcionPromo() {
        return descripcionPromo;
    }

    public boolean isStatus() {
        return status;
    }

    public String getFotoActual() {
        return fotoActual;
    }

    public int getIdFoto() {
        return idFoto;
    }
}
