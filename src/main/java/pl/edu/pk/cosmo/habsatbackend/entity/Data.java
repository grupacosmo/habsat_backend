package pl.edu.pk.cosmo.habsatbackend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="data")
@lombok.Data
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Double speed;

    @Column(nullable = false)
    Double altitude;

    @Column(nullable = false)
    Double longitude;

    @Column(nullable = false)
    Double latitude;

    @Column(nullable = false)
    Double temperature;

    @Column(nullable = false)
    LocalDateTime time;

    @Column(nullable = false)
    Double rssi;
}
