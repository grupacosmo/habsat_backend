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
    LocalDateTime time;

    @Column(nullable = false)
    LocalDateTime systemTime;

    @Column(nullable = false)
    Double fix;

    @Column(nullable = false)
    Double qualityLocation;

    @Column(nullable = false)
    Double speed;

    @Column(nullable = false)
    double altitude;

    @Column(nullable = false)
    int satellites;

    @Column(nullable = false)
    double temperature;

    @Column(nullable = false)
    double pressure;

    @Column(nullable = false)
    double accelerationX;

    @Column(nullable = false)
    double accelerationY;

    @Column(nullable = false)
    double accelerationZ;
}
