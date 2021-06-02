package com.example.abschlussprojekt_eddi;

public class BristolItem {

    private final String description_bristol_type;
    private final int img_bristol;

    public BristolItem(String description, int img){
        this.description_bristol_type = description;
        this.img_bristol = img;
    }

    public String getDescription_bristol_type(){
        return description_bristol_type;
    }

    public int getImg_bristol(){
        return img_bristol;
    }


}
