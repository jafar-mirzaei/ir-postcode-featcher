package ir.jm.iranpostcode.model.dto.entity;

import ir.jm.iranpostcode.model.dto.entity.interfaces.AbstractModel;

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
@Table(name = "BuildsRes")
public final class BuildsRes extends AbstractModel implements Serializable {
  private Set<Data> data;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "buildsRes", cascade = CascadeType.ALL, orphanRemoval = true)
  public Set<Data> getData() {
    return data;
  }

  public void setData(final Set<Data> data) {
    this.data = data;
  }
}

