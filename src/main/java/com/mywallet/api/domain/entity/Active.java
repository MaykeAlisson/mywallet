package com.mywallet.api.domain.entity;

import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actives")
@EqualsAndHashCode(of = "id")
public class Active implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ticket", nullable = false)
    private String ticket;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ActiveCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActiveType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private ActiveCurrency currency;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "evaluation")
    private Integer evaluation;

    @OneToMany(mappedBy = "active", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @CreatedDate
    @Column(name = "create_at")
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private Instant updateAt;

}
