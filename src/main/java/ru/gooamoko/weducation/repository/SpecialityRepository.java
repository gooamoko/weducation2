package ru.gooamoko.weducation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gooamoko.weducation.entity.Speciality;

/**
 * Репозиторий для раоты с сущностями Speciality.
 *
 * @author Voronin Leonid
 * @since 24.01.19
 **/
@Repository("specialityRepository")
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
}
