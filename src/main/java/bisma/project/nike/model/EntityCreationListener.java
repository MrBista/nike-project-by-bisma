package bisma.project.nike.model;

import bisma.project.nike.auth.UserDetailsImpl;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@EntityListeners(AuditingEntityListener.class)
public class EntityCreationListener {

    private static final Logger logger = LoggerFactory.getLogger(EntityCreationListener.class);
    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable) {
            Auditable auditable = (Auditable) entity;

            long currentDateEpoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;

            auditable.setCreatedAt(currentDateEpoch);
            auditable.setUpdatedAt(currentDateEpoch);
        }
        if (entity instanceof Product) {
            Product product = (Product) entity;


        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Auditable ) {
            Auditable auditable = (Auditable) entity;

            long currentDateEpoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;

            auditable.setUpdatedAt(currentDateEpoch);
        }
    }

    private String getLoggedInUsername() {
        // Retrieve the username of the logged-in user from your authentication mechanism
        // For example, using Spring Security:
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getEmail();
    }
//    @PrePersist
//    void onPrePersist(Product book) {
//        System.out.println("ProductListener.onPrePersist(): " + book);
//    }
//    @PostPersist
//    void onPostPersist(Product book) {
//        System.out.println("ProductListener.onPostPersist(): " + book);
//    }
//    @PostLoad
//    void onPostLoad(Product book) {
//        System.out.println("ProductListener.onPostLoad(): " + book);
//    }
//    @PreUpdate
//    void onPreUpdate(Product book) {
//        System.out.println("ProductListener.onPreUpdate(): " + book);
//    }
//    @PostUpdate
//    void onPostUpdate(Product book) {
//        System.out.println("ProductListener.onPostUpdate(): " + book);
//    }
//    @PreRemove
//    void onPreRemove(Product book) {
//        System.out.println("ProductListener.onPreRemove(): " + book);
//    }
//    @PostRemove
//    void onPostRemove(Product book) {
//        System.out.println("ProductListener.onPostRemove(): " + book);
//    }
}
