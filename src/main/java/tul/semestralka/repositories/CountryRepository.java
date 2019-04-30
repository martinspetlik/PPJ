package tul.semestralka.repositories;

import tul.semestralka.data.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends CrudRepository<Country, String> {

    boolean existsByTitle(String title);

    boolean existsByCode(String code);
}