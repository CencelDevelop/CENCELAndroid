package mx.com.cencel.comercial.cencel.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by iHouse on 06/08/15.
 */
public class ResultadoTramite implements Serializable {
    public int folio;
    public Date fechaIngresoData;

    public int getFolio() {
        return folio;
    }

    public Date getFechaIngresoData() {
        return fechaIngresoData;
    }

    public String getTipoTramite() {
        return TipoTramite;
    }

    public String getCodigoPlan() {
        return CodigoPlan;
    }

    public String getDescripcionPlan() {
        return DescripcionPlan;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getEstatusData() {
        return EstatusData;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getEstatusTELCEL() {
        return EstatusTELCEL;
    }

    public int getFolioSISACT() {
        return FolioSISACT;
    }

    public String TipoTramite;
    public String CodigoPlan;

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public void setFechaIngresoData(Date fechaIngresoData) {
        this.fechaIngresoData = fechaIngresoData;
    }

    public void setTipoTramite(String tipoTramite) {
        TipoTramite = tipoTramite;
    }

    public void setCodigoPlan(String codigoPlan) {
        CodigoPlan = codigoPlan;
    }

    public void setDescripcionPlan(String descripcionPlan) {
        DescripcionPlan = descripcionPlan;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstatusData(String estatusData) {
        EstatusData = estatusData;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setEstatusTELCEL(String estatusTELCEL) {
        EstatusTELCEL = estatusTELCEL;
    }

    public void setFolioSISACT(int folioSISACT) {
        FolioSISACT = folioSISACT;
    }

    public String DescripcionPlan;
    public String marca;
    public String modelo;
    public String EstatusData;
    public String nombreCliente;
    public String EstatusTELCEL;
    public int FolioSISACT;
}
