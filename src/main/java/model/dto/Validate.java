package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Validate {
    @JsonIgnore
    boolean isValid() throws Exception;

    @JsonIgnore
    String getEntityName();
}
