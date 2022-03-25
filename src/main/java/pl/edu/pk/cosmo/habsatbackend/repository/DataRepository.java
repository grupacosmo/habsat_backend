package pl.edu.pk.cosmo.habsatbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.entity.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {
}
