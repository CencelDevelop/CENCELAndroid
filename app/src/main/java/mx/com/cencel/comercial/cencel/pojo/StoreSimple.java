package mx.com.cencel.comercial.cencel.pojo;

import java.io.Serializable;

/**
 * Created by iHouse on 01/08/15.
 */
public class StoreSimple implements Serializable {
    private String code;
    private String name;

    public StoreSimple(String code, String name){
        super();
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
