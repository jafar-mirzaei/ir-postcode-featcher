package model.dto.entity;

import model.dto.AbstractModel;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
@Entity
@Table(name = "AUTOCOMPLETE_RES")
public final class AutocompleteRes extends AbstractModel implements Serializable {
  private String query;
  private Set<Suggestion> suggestions;

  public String getQuery() {
    return query;
  }

  public void setQuery(final String query) {
    this.query = query;
  }

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "autocompleteRes", cascade = CascadeType.ALL, orphanRemoval = true)
  public Set<Suggestion> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(final Set<Suggestion> suggestions) {
    this.suggestions = suggestions;
  }
}

