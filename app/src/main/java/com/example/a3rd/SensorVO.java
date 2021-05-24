package com.example.a3rd;

public class SensorVO {

    private String user_id;
    private String user_plant;
    private String plant_face;
    private String sensor_dank;
    private String sensor_temp;
    private String sensor_pir;

    public SensorVO(String user_id, String user_plant, String plant_face, String sensor_dank, String sensor_temp, String sensor_pir) {
        this.user_id = user_id;
        this.user_plant = user_plant;
        this.plant_face = plant_face;
        this.sensor_dank = sensor_dank;
        this.sensor_temp = sensor_temp;
        this.sensor_pir = sensor_pir;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_plant() {
        return user_plant;
    }

    public void setUser_plant(String user_plant) {
        this.user_plant = user_plant;
    }

    public String getPlant_face() {
        return plant_face;
    }

    public void setPlant_face(String plant_face) {
        this.plant_face = plant_face;
    }

    public String getSensor_dank() {
        return sensor_dank;
    }

    public void setSensor_dank(String sensor_dank) {
        this.sensor_dank = sensor_dank;
    }

    public String getSensor_temp() {
        return sensor_temp;
    }

    public void setSensor_temp(String sensor_temp) {
        this.sensor_temp = sensor_temp;
    }

    public String getSensor_pir() {
        return sensor_pir;
    }

    public void setSensor_pir(String sensor_pir) {
        this.sensor_pir = sensor_pir;
    }

    public SensorVO() {
    }
}
