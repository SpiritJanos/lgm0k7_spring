package com.spring.lgm0k7.user.entity;

import com.spring.lgm0k7.core.entity.CoreEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "app_role", schema = "public")
@Entity
public class AppRoleEntity extends CoreEntity implements GrantedAuthority {

    @Column(name = "authority")
    private String authority;

    @Override
    public String getAuthority() { return authority; }

    public void setAuthority(String authority) { this.authority = authority; }
}
