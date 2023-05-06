package ru.wb.bot.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность товара
 */
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "brand_name")
    private String brandName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_clients",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> subscribers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<UserEntity> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<UserEntity> subscribers) {
        this.subscribers = subscribers;
    }
}
