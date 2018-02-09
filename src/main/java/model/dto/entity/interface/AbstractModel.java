package model.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractModel implements Serializable {

    @Column(updatable = false)
    @JsonIgnore
    private int createdTime;
    @JsonIgnore
    private int updatedTime;
    @Column(columnDefinition = "boolean default false")
    private boolean block = false;

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public void setCreatedTime() {
        createdTime = (int) (System.currentTimeMillis() / 1000);
    }

    public int getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(int updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setUpdatedTime() {
        updatedTime = (int) (System.currentTimeMillis() / 1000);
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setEdit(AbstractModel am) {
        setId(am.getId());
        setCreatedTime(am.getCreatedTime());
        setBlock(am.isBlock());
        if(getId() > 0) {
            setUpdatedTime();
        }
    }
    private Long id;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      public Long getId() {
        return id;
      }

      public void setId(final Long id) {
        this.id = id;
      }
}