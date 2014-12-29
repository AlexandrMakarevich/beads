package com.lena.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 10.10.14.
 */
@Entity
@Table(name = "productgroup")
public class ProductGroup {

    public static final String ID = "id";

    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = ID)
    private Integer id;

    @NotNull(message = "productgroup.name can't be empty or null")
    @Column(name = NAME, length = 100)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(table = "productgroup", name = "parent_id")
    private List<ProductGroup> childGroups;

    @Column(name="parent_id")
    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductGroup> getChildGroups() {
        return childGroups;
    }

    public void setChildGroups(List<ProductGroup> childGroups) {
        this.childGroups = childGroups;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductGroup that = (ProductGroup) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
