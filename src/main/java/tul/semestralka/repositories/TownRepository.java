package tul.semestralka.repositories;

import tul.semestralka.data.Country;
import tul.semestralka.data.Town;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends CrudRepository<Town, Integer> {

    List<Town> findByName(String name);

    List<Town> findByCountry(Country country);

    Town getByNameAndCountry(String name, Country country);

    List<Town> getByCountryCode(String code);

    boolean existsByCountry_Code(String code);

    boolean existsByName(String name);

    @Query("select distinct t.country from Town as t")
    List<Country> getCountriesWithTown();
}
