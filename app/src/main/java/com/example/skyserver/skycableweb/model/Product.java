package com.example.skyserver.skycableweb.model;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class Product {
    private String Id;
    private String Name;
    private String Setop;
    private String Bno;
    private String CMonth;
  //  private String Datefrom;
    private String Payableamt;
    private String Mobile;
   // private String Dateto;
    private String Paidamt;
    private String Bal;
  //  private String Regno;
    private String Paydate;
    private String ADcust;
    private String Paydate1;
    private String Paidamt1;
    private String Area;
    private String PayStatus;
    private String CYear;
  //  private String Monthch;

    public Product(String id, String name, String setop, String bno, String month, String payableamt, String mobile,String paidamt, String bal, String paydate, String adcust, String paydate1, String paidamt1, String area, String payStatus, String cyear)

    {
        this.Id = id;
        this.Name = name;
        this.Setop = setop;
        this.Bno = bno;
        this.CMonth = month;
        //this.Datefrom = datefrom;
        this.Payableamt = payableamt;
        //this.Dateto = dateto;
        this.Mobile = mobile;
        this.Paidamt = paidamt;
        this.Bal = bal;
        //      this.Regno = regno;
        this.Paydate = paydate;
        this.ADcust = adcust;
        this.Paydate1 = paydate1;
        this.Paidamt1 = paidamt1;
        this.Area = area;
        this.PayStatus = payStatus;
        this.CYear = cyear;
        //this.Monthch = monthch;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSetop() {
        return Setop;
    }

    public void setSetop(String setop) {
        this.Setop = setop;
    }

    public String getBno() {return Bno; }

    public void setBno(String bno) {
        this.Bno = bno;
    }

    public String getCMonth() {return CMonth; }

    public void setCMonth(String month) {
        this.CMonth = month;
    }

    public String getPayableamt() {return Payableamt; }

    public void setPayableamt(String payableamt) {
        this.Bno = payableamt;
    }

    public String getPaid() {
        return Paidamt;
    }

    public void setPaid(String paidamt) {
        this.Paidamt = paidamt;
    }

//    public String getDatefrom() {
//        return Datefrom;
//    }
//
//    public void setDatefrom(String datefrom) {
//        this.Datefrom = datefrom;
//    }
//
//    public String getDateto() {
//        return Dateto;
//    }
//
//    public void setDateto(String dateto) {
//        this.Dateto = dateto;
//    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        this.Mobile = mobile;
    }

    public String getBal() {
        return Bal;
    }

    public void setBal(String bal) {
        this.Bal = bal;
    }

    public String getPaydate() {
        return Paydate;
    }

    public void setPaydate(String paydate) {
        this.Paydate = paydate;
    }

    public String getADcust() {
        return ADcust;
    }

    public void setADcust(String adcust) {
        this.Paydate = adcust;
    }


    public String getPaydate1() {
        return Paydate1;
    }

    public void setPaydate1(String paydate1) {
        this.Paydate1 = paydate1;
    }


    public String getPaid1() {
        return Paidamt1;
    }

    public void setPaid1(String paidamt1) {
        this.Paidamt1 = paidamt1;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        this.Area = area;
    }

    public String getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(String payStatus) {
        this.PayStatus = payStatus;
    }

    public String getCYear() {
        return CYear;
    }

    public void setCYear(String cYear) {
        this.CYear = cYear;
    }

//    public String getMonthch() {
//        return Monthch;
//    }
//
//    public void setMonthch(String monthch) {
//        this.Monthch = monthch;
//    }
}
