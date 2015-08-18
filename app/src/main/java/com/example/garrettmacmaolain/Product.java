package com.example.garrettmacmaolain;

import android.graphics.Bitmap;

import com.parse.ParseObject;

/**
 * Created by jesper on 17/11/14.
 */
public class Product {

    ProductImageSetListener productImageSetListener;

    private String objectID;
    private String owner;
    private String ownerName;
    private String title;
    private Bitmap image;
    private String brand;
    private String category;
    private String color;
    private String material;
    private String size;

    public Product(){

    }
    public Product(String objectID){
        this.objectID = objectID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "objectID='" + objectID + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", title='" + title + '\'' +
                ", image=" + image +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", material='" + material + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
    public Product(ParseObject po,ProductImageSetListener listener){

        this.objectID = po.getObjectId();
        this.owner = po.getString(ParseStrings.PRODUCT_OWNER);
        this.ownerName = po.getString(ParseStrings.PRODUCT_OWNER_NAME);
        this.title = po.getString(ParseStrings.PRODUCT_TITLE);
        this.brand = po.getString(ParseStrings.PRODUCT_BRAND);
        this.category = po.getString(ParseStrings.PRODUCT_CATEGORY);
        this.color = po.getString(ParseStrings.PRODUCT_COLOR);
        this.material = po.getString(ParseStrings.PRODUCT_MATERIAL);
        this.size = po.getString(ParseStrings.PRODUCT_SIZE);
        ParseAPI.getImageFromFile(po.getParseFile(ParseStrings.PRODUCT_IMAGE), Product.this,listener);
        //getImageFromFile(po.getParseFile(ParseStrings.PRODUCT_IMAGE),listener);

    }
    public Product(String objectID,String owner,String ownerName, String title, String brand, String category, String color, String material, String size) {
        this.objectID = objectID;
        this.owner = owner;
        this.ownerName = ownerName;
        this.title = title;
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.material = material;
        this.size = size;
    }
    public Product(String objectID,String owner, String ownerName, String title, String brand, String category, String color, String material, String size, Bitmap image) {
        this.objectID = objectID;
        this.owner = owner;
        this.ownerName = ownerName;
        this.title = title;
        this.image = image;
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.material = material;
        this.size = size;
    }
    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public interface ProductImageSetListener {
        public void onProductImageSet(Product product);
    }

}
