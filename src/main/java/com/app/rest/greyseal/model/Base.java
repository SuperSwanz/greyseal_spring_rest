package com.app.rest.greyseal.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class Base implements Serializable {


    private static final long serialVersionUID = -5027575885711586692L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;

    @Column(name = "created_by", nullable = true, updatable = false)
    private User createdBy;

    @Column(name = "updated_by", nullable = true)
    private User updatedBy;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @PrePersist
    protected void onCreate() {
        final Date dt = new Date();
        this.createdDate = dt;
        this.updatedDate = dt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(32).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Base base = (Base) obj;
        return new EqualsBuilder().append(this.id, base.id).isEquals();
    }
}
