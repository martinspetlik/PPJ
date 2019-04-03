package tul.semestralka.repositories;

import tul.semestralka.data.Country;
import tul.semestralka.data.Town;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends CrudRepository<Town, Integer> {

    @Query("select t from Town t where t.name=:name")
    List<Town> findByName(@Param("name") String name);

    @Query("select t from Town t WHERE t.country=:country")
    List<Town> findByCountry(@Param("country") Country country);
}
