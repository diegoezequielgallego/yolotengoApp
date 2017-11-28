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
