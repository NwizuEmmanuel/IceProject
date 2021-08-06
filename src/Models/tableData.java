/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Onyekachukwu
 */
public class tableData {
    private String id,FirstName, LastName, OtherName, Gender, Telephone, Email, Address, Course, RegistrationDate, PGContact;
    private Double stillPaying;

    public tableData(String id,String FirstName, String LastName, String OtherName, String Gender, String Telephone, String Email, String Address, String Course, String RegistrationDate, String PGContact) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.OtherName = OtherName;
        this.Gender = Gender;
        this.Telephone = Telephone;
        this.Email = Email;
        this.Address = Address;
        this.Course = Course;
        this.RegistrationDate = RegistrationDate;
        this.PGContact = PGContact;
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getOtherName() {
        return OtherName;
    }

    public String getGender() {
        return Gender;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getCourse() {
        return Course;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public String getPGContact() {
        return PGContact;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setOtherName(String OtherName) {
        this.OtherName = OtherName;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setCourse(String Course) {
        this.Course = Course;
    }

    public void setRegistrationDate(String RegistrationDate) {
        this.RegistrationDate = RegistrationDate;
    }

    public void setPGContact(String PGContact) {
        this.PGContact = PGContact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
