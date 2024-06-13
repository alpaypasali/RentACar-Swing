package entity;

import entity.enums.Fuel;
import entity.enums.Gear;
import entity.enums.Type;

public class Model {
    private int id;
    private  int brand_id;
    private  String name;
    private Type type;
    private  int year;
    private Fuel fuel;
    private Gear gear;
    private Brand brand;

    public Model(int id, int brand_id, String name, Type type, int year, Fuel fuel, Gear gear) {
        this.id = id;
        this.brand_id = brand_id;
        this.name = name;
        this.type = type;
        this.year = year;
        this.fuel = fuel;
        this.gear = gear;
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
