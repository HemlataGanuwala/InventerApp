package com.example.skyserver.skycableweb.model;

public class Product1 {
    private String Billno;
    private String Cmonth;
    private String Paid1;
    private String Paid2;
    private String Paydt1;
    private String Paiddt2;
    private String Balance;

    public Product1(String bno,String cmonth,String paid1,String paid2,String paydt1,String paiddt2,String balance) {
        this.Billno = bno;
        this.Cmonth = cmonth;
        this.Paid1 = paid1;
        this.Paid2 = paid2;
        this.Paydt1 = paydt1;
        this.Paiddt2 = paiddt2;
        this.Balance = balance;
    }

    public String getBillno() {
        return Billno;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getCmonth() {
        return Cmonth;
    }

    public void setCmonth(String cmonth) {
        Cmonth = cmonth;
    }

    public String getPaid1() {
        return Paid1;
    }

    public void setPaid1(String paid1) {
        Paid1 = paid1;
    }

    public String getPaid2() {
        return Paid2;
    }

    public void setPaid2(String paid2) {
        Paid2 = paid2;
    }

    public String getPaiddt2() {
        return Paiddt2;
    }

    public void setPaiddt2(String paiddt2) {
        Paiddt2 = paiddt2;
    }

    public String getPaydt1() {
        return Paydt1;
    }

    public void setPaydt1(String paydt1) {
        Paydt1 = paydt1;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBillno(String billno) {
        Billno = billno;
    }
}
