package ma.mahboubi.hopital.repository;

import ma.mahboubi.hopital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByNameContains (String kw, Pageable pageable);
    @Query("select p from Patient p where p.name like :x")
    Page<Patient> chercher (@Param("x") String keyword, Pageable pageable);
}
