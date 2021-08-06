/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LedgerModels;

import java.sql.Date;





/**
 *
 * @author Onyekachukwu
 */
public class ledgerModels {
    Integer ID;
    String Descriptions;
    Double Income;
    Double Expense;
    Date date;

    public ledgerModels(Integer ID, String Descriptions, Double Income, Double Expense, Date date) {
        this.ID = ID;
        this.Descriptions = Descriptions;
        this.Income = Income;
        this.Expense = Expense;
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public Double getIncome() {
        return Income;
    }

    public Double getExpense() {
        return Expense;
    }

    public Date getDate() {
        return date;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setDescriptions(String Descriptions) {
        this.Descriptions = Descriptions;
    }

    public void setIncome(Double Income) {
        this.Income = Income;
    }

    public void setExpense(Double Expense) {
        this.Expense = Expense;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
