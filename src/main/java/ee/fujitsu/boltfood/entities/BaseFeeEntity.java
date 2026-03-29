package ee.fujitsu.boltfood.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "BASE_FEE")
public class BaseFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "CITY", nullable = false)
    private String city;

    @NotNull
    @Column(name = "REGIONAL_BASE_FEE", nullable = false)
    private Float regionalBaseFee;

    @NotNull
    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TRANSPORT_ID")
    private TransportEntity transport;
}