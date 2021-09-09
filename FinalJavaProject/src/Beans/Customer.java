package Beans;


import java.util.ArrayList;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Coupon> coupon;

    /**
     * This class is in charge of creating, updating and getting  customers instance and values of it
     * This class has 6 attributes from types String, Integer and ArrayList. and methods as written below
     *
     */


    /**
     * This is constructor without id because it is auto increment attribute. gives us instance of customer type
     *
     * @param firstName this is the first name of the customer
     * @param lastName  this is the last name of the customer
     * @param email     this is the email of the customer
     * @param password  this is the password of the customer
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * This is full constructor. gives us instance of customer type
     *
     * @param id        this is the id number of the customer
     * @param firstName this is the first name of the customer
     * @param lastName  this is the last name of the customer
     * @param email     this is the email of the customer
     * @param password  this is the password of the customer
     * @param coupon    this is an ArrayList of all the coupons that the customer bought
     */
    public Customer(int id, String firstName, String lastName, String email, String password, ArrayList<Coupon> coupon) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupon = coupon;
    }
    /**
     * This is constructor without id because it is auto increment attribute. gives us instance of customer type
     * and with no list of the coupons of the customer
     * @param firstName this is the first name of the customer
     * @param lastName  this is the last name of the customer
     * @param email     this is the email of the customer
     * @param password  this is the password of the customer
     */
    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * A getter method for customer id
     * @return customer id
     */
    public int getId() {
        return id;
    }

    /**
     * A getter method for customer first name
     * @return customer customer name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * A getter method for customer last name
     * @return customer customer last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * A getter method for customer email
     * @return customer email
     */
    public String getEmail() {
        return email;
    }


    /**
     * A getter method for customer password
     * @return customer password
     */
    public String getPassword() {
        return password;
    }



    /**
     * This is toString method to show customer values
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n");
        for (Coupon item : coupon) {
            str.append(item + "\n");
        }
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + str +
                '}';
    }
}
