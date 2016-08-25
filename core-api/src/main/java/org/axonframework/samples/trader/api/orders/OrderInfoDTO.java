package org.axonframework.samples.trader.api.orders;

/**
 * Created by DELL-PC on 7/4/2016.
 */
public class OrderInfoDTO {

    private String email;
    private String payerId;
    private String firstName;
    private String lastName;
    private String cntryCode;
    private String shipToName;
    private String shipToStreet;
    private String shipToCity;
    private String shipToState;
    private String shipToCntryCode;
    private String shipToZip;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPayerId() { return payerId; }

    public void setPayerId(String payerId) { this.payerId = payerId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCntryCode() { return cntryCode; }

    public void setCntryCode(String cntryCode) { this.cntryCode = cntryCode; }

    public String getShipToName() { return shipToName; }

    public void setShipToName(String shipToName) { this.shipToName = shipToName; }

    public String getShipToStreet() { return shipToStreet; }

    public void setShipToStreet(String shipToStreet) { this.shipToStreet = shipToStreet; }

    public String getShipToState() { return shipToState; }

    public void setShipToState(String shipToState) { this.shipToState = shipToState; }

    public String getShipToCity() { return shipToCity; }

    public void setShipToCity(String shipToCity) { this.shipToCity = shipToCity; }

    public String getShipToCntryCode() { return shipToCntryCode; }

    public void setShipToCntryCode(String shipToCntryCode) { this.shipToCntryCode = shipToCntryCode; }

    public String getShipToZip() { return shipToZip; }

    public void setShipToZip(String shipToZip) { this.shipToZip = shipToZip; }
}