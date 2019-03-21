package tul.semestralka.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CountryDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Transactional
    public boolean create(Country country) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("title", country.getTitle());
        params.addValue("code", country.getCode());

        return jdbc.update("insert into country " +
                "(title, code) " +
                "values (:title, :code)", params) == 1;
    }

    public boolean exists(String title) {
        return jdbc.queryForObject("select count(*) from country where title=:title",
                new MapSqlParameterSource("title", title), Integer.class) > 0;
    }

    public List<Country> getAllCountries() {
        return jdbc.query("select * from country", BeanPropertyRowMapper.newInstance(Country.class));
    }

    public void deleteCountries() {
        jdbc.getJdbcOperations().execute("DELETE FROM town");
        jdbc.getJdbcOperations().execute("DELETE FROM country");
    }
}
