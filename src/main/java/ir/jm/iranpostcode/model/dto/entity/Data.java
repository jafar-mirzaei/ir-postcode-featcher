package ir.jm.iranpostcode.model.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.jm.iranpostcode.model.dto.entity.interfaces.AbstractModel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
@Entity
@Table(name = "Data", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"rowguid"}, name = "UK_DATA_ROWGUID")})
public final class Data extends AbstractModel implements Serializable {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_Data", foreignKey = @ForeignKey(name = "FK_BUILDSRES_ID"))
  @JsonIgnore
  private BuildsRes buildsRes;

  public BuildsRes getBuildsRes() {
    return buildsRes;
  }

  public void setBuildsRes(final BuildsRes buildsRes) {
    this.buildsRes = buildsRes;
  }

  private String houseNo;

  private String des;

  private String rowguid;

  private String floor;

  private String side;

  private String preAven;

  private String sideNo;

  private String aven;

  public String getHouseNo() {
    return houseNo;
  }

  public void setHouseNo(String houseNo) {
    this.houseNo = houseNo;
  }

  public String getDes() {
    return des;
  }

  public void setDes(String des) {
    this.des = des;
  }

  public String getRowguid() {
    return rowguid;
  }

  public void setRowguid(String rowguid) {
    this.rowguid = rowguid;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getPreAven() {
    return preAven;
  }

  public void setPreAven(String preAven) {
    this.preAven = preAven;
  }

  public String getSideNo() {
    return sideNo;
  }

  public void setSideNo(String sideNo) {
    this.sideNo = sideNo;
  }

  public String getAven() {
    return aven;
  }

  public void setAven(String aven) {
    this.aven = aven;
  }

  @Override
  public String toString() {
    return "ClassPojo [houseNo = " + houseNo + ", des = " + des + ", rowguid = " + rowguid + ", floor = " + floor +
           ", side = " + side + ", preAven = " + preAven + ", sideNo = " + sideNo + ", aven = " + aven + "]";
  }
}

