package com.example.a3rd;

public class bookVO {
    private String plantName;
    private String plantImg;
    private String plantSoilhum;
    private String plantTemper;
    private String plantInfo;

    public bookVO() {
    }

    public bookVO(String plantName, String plantImg, String plantSoilhum, String plantTemper, String plantInfo) {
        this.plantName = plantName;
        this.plantImg = plantImg;
        this.plantSoilhum = plantSoilhum;
        this.plantTemper = plantTemper;
        this.plantInfo = plantInfo;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantImg() {
        return plantImg;
    }

    public void setPlantImg(String plantImg) {
        this.plantImg = plantImg;
    }

    public String getPlantSoilhum() {
        return plantSoilhum;
    }

    public void setPlantSoilhum(String plantSoilhum) {
        this.plantSoilhum = plantSoilhum;
    }

    public String getPlantTemper() {
        return plantTemper;
    }

    public void setPlantTemper(String plantTemper) {
        this.plantTemper = plantTemper;
    }

    public String getPlantInfo() {
        return plantInfo;
    }

    public void setPlantInfo(String plantInfo) {
        this.plantInfo = plantInfo;
    }
}
