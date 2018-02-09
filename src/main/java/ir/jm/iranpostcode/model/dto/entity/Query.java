package ir.jm.iranpostcode.model.dto.entity;

import ir.jm.iranpostcode.model.dto.entity.interfaces.AbstractModel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
@Entity
@Table(name = "QUERY"/*, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"query", "houseNum"}, name = "UK_QUERY_houseNum_query")}*/)
public final class Query extends AbstractModel implements Serializable {
  private int houseNum;
  private String query;

  public int getHouseNum() {
    return houseNum;
  }

  public void setHouseNum(final int houseNum) {
    this.houseNum = houseNum;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(final String query) {
    this.query = query;
  }
}

