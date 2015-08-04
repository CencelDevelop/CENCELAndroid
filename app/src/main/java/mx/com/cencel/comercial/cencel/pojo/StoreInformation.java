package mx.com.cencel.comercial.cencel.pojo;

import java.io.Serializable;

public class StoreInformation implements Serializable {
    private int id;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeEmail;
    private String storeCoordinate;

    public StoreInformation(int id, String storeName, String storeAddress, String storePhone, String storeEmail, String storeCoordinate) {
        this.id = id;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeEmail = storeEmail;
        this.storeCoordinate = storeCoordinate;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public String getStoreCoordinate() {
        return storeCoordinate;
    }

    public int getId(){
        return this.id;
    }
}
