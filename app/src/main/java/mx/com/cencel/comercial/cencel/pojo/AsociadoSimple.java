package mx.com.cencel.comercial.cencel.pojo;

import java.io.Serializable;

public class AsociadoSimple implements Serializable {
    private String guid;
    private int responseCode;



    public AsociadoSimple(String guid, int responseCode){
        super();
        this.guid = guid;
        this.responseCode = responseCode;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getGuid() {
        return guid;
    }

    public int getResponseCode() {
        return responseCode;
    }





}
