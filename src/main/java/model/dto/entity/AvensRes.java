package model.dto.entity;

import model.dto.AbstractModel;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
@Entity
@Table(name = "AVENSRES")
public final class AvensRes extends AbstractModel implements Serializable {
  private Set<Datum> data;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "avensRes", cascade = CascadeType.ALL, orphanRemoval = true)
  public Set<Datum> getData() {
    return data;
  }

  public void setData(final Set<Datum> data) {
    this.data = data;
  }
}

