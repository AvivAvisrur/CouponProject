package Beans;

import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    /**
     * This class is in charge of creating, updating and getting  company instance and values of it.
     * This class has 5 attributes from types String, Integer and ArrayList. and methods as written below.
     *
     */

    /**
     * This is constructor without array list of the company's coupons.
     *
     * @param id       this is the id number of the company.
     * @param name     this is the  name of the company.
     * @param email    this is the email of the company.
     * @param password this is the password of the company.
     * @param coupons  this is the list of the coupons that company sells.
     */
    public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.coupons = coupons;
    }
    /**
     * This is constructor without array list of the company's coupons.
     *
     * @param id       this is the id number of the company.
     * @param name     this is the  name of the company.
     * @param email    this is the email of the company.
     * @param password this is the password of the company.
     *
     */
    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }



    /**
     * This is constructor  with no array list used only to add new company. and no id because it it auto increment.
     *
     * @param name     this is the  name of the company.
     * @param email    this is the email of the company.
     * @param password this is the password of the company.
     */

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * This is  constructor  with no arraylist and no name used only to update company .
     *
     * @param email    this is the email of the company.
     * @param password this is the password of the company.
     */
    public Company(String email, String password) {
        this.email = email;
        this.password = password;
    }


    /**
     * A getter method for company id
     *
     * @return company id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for company name
     *
     * @return name of company
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for company email
     *
     * @return email of company
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method for company password
     *
     * @return password of company
     */
    public String getPassword() {
        return password;
    }


    /**
     * This is toString method to show companies values.
     *
     * @return String.
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }


}
