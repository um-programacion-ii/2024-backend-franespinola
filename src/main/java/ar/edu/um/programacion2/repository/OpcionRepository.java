package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Opcion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opcion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Long> {}
