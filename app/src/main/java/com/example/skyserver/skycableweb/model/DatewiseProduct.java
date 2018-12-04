package com.example.skyserver.skycableweb.model;

public class DatewiseProduct {
    private String Dname;
    private String DSetupbox;
    private String DBillno;
    private String DPaidAmt;
    private String DPaydate;


    public DatewiseProduct(String name,String setup,String bill,String payamt,String paydt) {
        this.Dname = name;
        this.DSetupbox = setup;
        this.DBillno = bill;
        this.DPaidAmt = payamt;
        this.DPaydate = paydt;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        this.Dname = dname;
    }

    public String getDSetupbox() {
        return DSetupbox;
    }

    public void setDSetupbox(String dSetupbox) {
        this.DSetupbox = dSetupbox;
    }

    public String getDBillno() {
        return DBillno;
    }

    public void setDBillno(String dBillno) {
        this.DBillno = dBillno;
    }

    public String getDPaidAmt() {
        return DPaidAmt;
    }

    public void setDPaidAmt(String dPaidAmt) {
        this.DPaidAmt = dPaidAmt;
    }

    public String getDPaydate() {
        return DPaydate;
    }

    public void setDPaydate(String dPaydate) {
        this.DPaydate = dPaydate;
    }
}
