package model.dto.entity;

import model.dto.AbstractModel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
@Entity
@Table(name = "POSTCODERES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"postcode"}, name = "UK_POSTCODERES_postcode")})
public final class PostCodeRes extends AbstractModel implements Serializable {
  private String status;

  public String getStatus() { return this.status; }

  public void setStatus(String status) { this.status = status; }

  private String postcode;

  public String getPostcode() { return this.postcode; }

  public void setPostcode(String postcode) { this.postcode = postcode; }

  private int gnaf_id;

  public int getGnafId() { return this.gnaf_id; }

  public void setGnafId(int gnaf_id) { this.gnaf_id = gnaf_id; }

  private String sub;

  public String getSub() { return this.sub; }

  public void setSub(String sub) { this.sub = sub; }

  private String street;

  public String getStreet() { return this.street; }

  public void setStreet(String street) { this.street = street; }

  private String street2;

  public String getStreet2() { return this.street2; }

  public void setStreet2(String street2) { this.street2 = street2; }

  private String bldname;

  public String getBldname() { return this.bldname; }

  public void setBldname(String bldname) { this.bldname = bldname; }

  private String des;

  public String getDes() { return this.des; }

  public void setDes(String des) { this.des = des; }

  private String houseNo;

  public int getGnaf_id() {
    return gnaf_id;
  }

  public void setGnaf_id(final int gnaf_id) {
    this.gnaf_id = gnaf_id;
  }

  public String getHouseNo() {
    return houseNo;
  }

  public void setHouseNo(final String houseNo) {
    this.houseNo = houseNo;
  }

  private int floorNo;

  public int getFloorNo() { return this.floorNo; }

  public void setFloorNo(int floorNo) { this.floorNo = floorNo; }

  private String sideFloor;

  public String getSideFloor() { return this.sideFloor; }

  public void setSideFloor(String sideFloor) { this.sideFloor = sideFloor; }

  @Override public String toString() {
    final StringBuilder sb = new StringBuilder("PostCodeRes{");
    if (status != null) {
      sb.append("status='").append(status).append('\'');
    }
    if (postcode != null) {
      sb.append(", postcode='").append(postcode).append('\'');
    }
    sb.append(", gnaf_id=").append(gnaf_id);

    sb.append(", sub='").append(sub).append('\'');

    if (street != null) {
      sb.append(", street='").append(street).append('\'');
    }
    if (street2 != null) {
      sb.append(", street2='").append(street2).append('\'');
    }
    if (bldname != null) {
      sb.append(", bldname='").append(bldname).append('\'');
    }
    if (des != null) {
      sb.append(", des='").append(des).append('\'');
    }
    if (houseNo != null) {
      sb.append(", houseNo='").append(houseNo).append('\'');
    }
    sb.append(", floorNo=").append(floorNo);

    if (sideFloor != null) {
      sb.append(", sideFloor='").append(sideFloor).append('\'');
    }
    sb.append('}');
    return sb.toString();
  }
}

