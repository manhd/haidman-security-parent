package com.haidm.web.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author shs-xxaqbzymhd
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 520L;
    private final Object principal;  // 认证前是手机号码，认证后是用户信息


    /**
     * 认证之前 使用的构造方法，吃方法会标识未认证
     * @param principal
     */
    public MobileAuthenticationToken(Object principal) {
        super((Collection)null);
        this.principal = principal;
        this.setAuthenticated(false);
    }

    /**
     * 认证通过之后，会重新创建 MobileAuthenticationToken 实例，来进行封装认证信息
     * @param principal 用户信息
     * @param authorities 权限资源
     */
    public MobileAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    /**
     * 因为是父类中的抽象方法，所以必须要实现
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
