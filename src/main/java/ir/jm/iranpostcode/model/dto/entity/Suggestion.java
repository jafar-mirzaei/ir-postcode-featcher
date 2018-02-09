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

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
@Entity
@Table(name = "SUGGESTION"/*,uniqueConstraints = @UniqueConstraint(columnNames = {"FK_AUTOCOMPLETE_RES_id","data"})*/)
public final class Suggestion extends AbstractModel implements Serializable {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_AUTOCOMPLETE_RES", foreignKey = @ForeignKey(name = "FK_AUTOCOMPLETE_RES_id"))
  @JsonIgnore
  private AutocompleteRes autocompleteRes;
  private String value;
  private String data;

  public String getData() {
    return data;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public AutocompleteRes getAutocompleteRes() {
    return autocompleteRes;
  }

  public void setAutocompleteRes(final AutocompleteRes autocompleteRes) {
    this.autocompleteRes = autocompleteRes;
  }
}

