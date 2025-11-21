package com.example.myapplication.data.models;

public class Product {
    private Integer id;
    private String name;
    private String type;
    private String material;
    private int proba;
    private double price;
    private String status;
    private String description;
    private String image_url;
    private String created_at;

    // ==================== КОНСТРУКТОРЫ ====================

    public Product() {
    }

    public Product(int id, String name, String type, String material, int proba,
                   double price, String status, String description, String image_url, String created_at) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.material = material;
        this.proba = proba;
        this.price = price;
        this.status = status;
        this.description = description;
        this.image_url = image_url;
        this.created_at = created_at;
    }

    public Product(String name, String type, String material, int proba,
                   double price, String status, String description, String image_url) {
        this.name = name;
        this.type = type;
        this.material = material;
        this.proba = proba;
        this.price = price;
        this.status = status;
        this.description = description;
        this.image_url = image_url;
        this.created_at = String.valueOf(System.currentTimeMillis());
    }

    public Product(int id, String name, String type, double price,
                   String description, String image_url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getProba() {
        return proba;
    }

    public void setProba(int proba) {
        this.proba = proba;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", material='" + material + '\'' +
                ", proba=" + proba +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", image_url='" + image_url + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public boolean isValidForCreation() {
        return name != null && !name.isEmpty() &&
                type != null && !type.isEmpty() &&
                material != null && !material.isEmpty() &&
                proba > 0 &&
                price > 0 &&
                image_url != null && !image_url.isEmpty();
    }

    public boolean isAvailable() {
        return "available".equalsIgnoreCase(status) ||
                "в наличии".equalsIgnoreCase(status);
    }

    public String getProbaInfo() {
        switch (proba) {
            case 375:
                return "375 проба (9 каратов)";
            case 585:
                return "585 проба (14 каратов)";
            case 750:
                return "750 проба (18 каратов)";
            case 999:
                return "999 проба (чистое золото)";
            default:
                return proba + " проба";
        }
    }
}
