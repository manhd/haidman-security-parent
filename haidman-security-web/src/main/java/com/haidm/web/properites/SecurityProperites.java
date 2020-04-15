package com.haidm.web.properites;

import com.haidm.web.properites.AuthenticationProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "haidman.security")
@Data
public class SecurityProperites {
    private AuthenticationProperties authentication;
}
