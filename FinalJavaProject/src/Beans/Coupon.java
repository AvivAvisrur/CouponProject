package Beans;
import java.sql.Date;

/**
 * this class represents a coupon
 */
public class Coupon {
    private int id;
    private int companyId;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;


    /**
     * all arguments constructor
     * @param id a coupon's id number
     * @param companyId the company id the coupon belongs to
     * @param category enum representing the category type the coupon belongs to
     * @param title title of the coupon
     * @param description description of the coupon
     * @param startDate the start date of the sale of the coupon
     * @param endDate the end date of the sale of the coupon
     * @param amount the amount of the coupons to be sold
     * @param price the price of the coupon
     * @param image a string representing location of the image of the coupon
     */
    public Coupon(int id, int companyId, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id = id;
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * a constructor for a coupon instance that is to be sent to the database containing no id parameter(to be added by the database)
     * @param companyId the company id the coupon belongs to
     * @param category enum representing the category type the coupon belongs to
     * @param title title of the coupon
     * @param description description of the coupon
     * @param startDate the start date of the sale of the coupon
     * @param endDate the end date of the sale of the coupon
     * @param amount the amount of the coupons to be sold
     * @param price the price of the coupon
     * @param image a string representing location of the image of the coupon
     *
     */

    public Coupon(int companyId, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * id getter method
     * @return coupon's id field
     */
    public int getId() {
        return id;
    }


    /**
     * companyId field getter method
     * @return the coupon's companyId field
     */
    public int getCompanyId() {
        return companyId;
    }


    /**
     * category field getter method
     * @return the coupon's category field
     */
    public Category getCategory() {
        return category;
    }



    /**
     * title field getter method
     * @return the coupon's title field
     */
    public String getTitle() {
        return title;
    }



    /**
     * description field getter method
     * @return the coupon's description field
     */
    public String getDescription() {
        return description;
    }


    /**
     * startDate field getter method
     * @return the coupon's startDate field
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * endDate field getter method
     * @return the coupon's sale endDate field
     */
    public Date getEndDate() {
        return endDate;
    }


    /**
     * amount field getter method
     * @return the coupon's amount field
     */
    public int getAmount() {
        return amount;
    }



    /**
     * price field getter method
     * @return the coupon's price field
     */
    public double getPrice() {
        return price;
    }



    /**
     * image field getter method
     * @return the coupon's image field
     */
    public String getImage() {
        return image;
    }



    /**
     * a method overriding the default Object toString
     * @return Intellij's auto generated toString of the coupon's fields
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyId=" + companyId +
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
