package bisma.project.nike.model;

import jakarta.persistence.*;

import java.sql.Timestamp;


@MappedSuperclass
public class Auditable {

    @Column(name = "created_at", nullable = false, updatable = false)
    private long createdAt;

    @Column(name = "updated_at")
    private long updatedAt;


    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

}
