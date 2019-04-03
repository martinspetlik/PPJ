package tul.semestralka.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tul.semestralka.data.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tul.semestralka.data.Town;

import java.util.List;


@Repository
public interface CountryRepository extends CrudRepository<Country, String> {

    @Query(value = "SELECT c FROM Country c WHERE c.title=:title")
    public List<Country> existsByTitle(@Param("title") String title);



}