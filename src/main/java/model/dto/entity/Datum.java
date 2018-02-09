package model.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.dto.AbstractModel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Datum", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aven_id","houseNo","pre_aven_id"}, name = "UK_Datum_all")})
public class Datum extends AbstractModel implements Serializable {

  private AvensRes avensRes;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_Datum", foreignKey = @ForeignKey(name = "FK_AVENSRES_ID"))
  @JsonIgnore
  public AvensRes getAvensRes() {
    return avensRes;
  }

  public void setAvensRes(final AvensRes avensRes) {
    this.avensRes = avensRes;
  }

  private String parish;

  public String getParish() { return this.parish; }

  public void setParish(String parish) { this.parish = parish; }

  private String preAven;

  public String getPreAven() { return this.preAven; }

  public void setPreAven(String preAven) { this.preAven = preAven; }

  private String aven;

  public String getAven() { return this.aven; }

  public void setAven(String aven) { this.aven = aven; }

  private int houseNo;

  public int getHouseNo() { return this.houseNo; }

  public void setHouseNo(int houseNo) { this.houseNo = houseNo; }

  private int aven_id;

  public int getAvenId() { return this.aven_id; }

  public void setAvenId(int aven_id) { this.aven_id = aven_id; }

  private int pre_aven_id;

  public int getPreAvenId() { return this.pre_aven_id; }

  public void setPreAvenId(int pre_aven_id) { this.pre_aven_id = pre_aven_id; }

  private String name;

  public String getName() { return this.name; }

  public void setName(String name) { this.name = name; }

  public int getAven_id() {
    return aven_id;
  }

  public void setAven_id(final int aven_id) {
    this.aven_id = aven_id;
  }

  public int getPre_aven_id() {
    return pre_aven_id;
  }

  public void setPre_aven_id(final int pre_aven_id) {
    this.pre_aven_id = pre_aven_id;
  }
}