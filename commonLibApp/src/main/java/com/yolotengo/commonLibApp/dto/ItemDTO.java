package com.yolotengo.commonLibApp.dto;

/**
 * Created by dgallego on 27/11/2017.
 */
public class ItemDTO {

    public ItemDTO(String name, String description){
        this.name = name;
        this.description = description;
    }

    private String name;
    private String description;
    private String gotItem;
    private String price;

    public String getGotItem() {
        return gotItem;
    }

    public void setGotItem(String gotItem) {
        this.gotItem = gotItem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
