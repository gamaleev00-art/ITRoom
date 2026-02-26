package com.example.listner;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
public class LoginAttemptsLogger {

    @EventListener
    public void auditEventsHappens(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        log.info("Principal {} - {}", auditEvent.getPrincipal(), auditEvent.getType());
        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
        log.info("Remote IP address: {}", details.getRemoteAddress());
        log.info("Session Id: {}", details.getSessionId());
    }
}
