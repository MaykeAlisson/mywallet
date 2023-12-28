package com.mywallet.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private Long id;

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
    private Long quantity;

    @Column(name = "pvp")
    private BigDecimal pvp;

    @Column(name = "pl")
    private BigDecimal pl;

    @Column(name = "pm")
    private BigDecimal pm;

    @OneToMany(mappedBy = "active", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Report> reports;

    @OneToMany(mappedBy = "active", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Launch> launchs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Column(name = "create_at")
    @CreationTimestamp
    private Instant createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Instant updateAt;

}
