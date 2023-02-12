package Beans;

import enums.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Coupon {
    private long id;
    private long companyID;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon(long id, long companyID, Category category, String title, String description, String startDate, String endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public Coupon(long companyID, Category category, String title, String description, String startDate, String endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(long companyID) {
        this.companyID = companyID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;}

    public Date getStartDate() {
        return startDate;
    }
    public String getSimpleStartDate(){
        return (new SimpleDateFormat("yyyy-MM-dd")).format(startDate);
    }
    public void setStartDate(String startDate) {
        try {this.startDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getEndDate() {
        return endDate;
    }
    public String getSimpleEndDate(){
        return (new SimpleDateFormat("yyyy-MM-dd")).format(endDate);
    }

    public void setEndDate(String endDate) {
        try {this.endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(endDate);}
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Beans.Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
