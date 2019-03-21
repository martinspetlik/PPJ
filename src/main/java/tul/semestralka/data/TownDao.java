package tul.semestralka.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class TownDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;


    public List<Town> getTowns() {

        return jdbc
                .query("select * from town join country using (code)",
                        (ResultSet rs, int rowNum) -> {
                            Country country = new Country();
                            country.setCode(rs.getInt("code"));
                            country.setTitle(rs.getString("title"));

                            Town town = new Town();
                            town.setId(rs.getInt("id"));
                            town.setName(rs.getString("name"));
                            town.setCountry(country);

                            return town;
                        }
                );
    }


    public boolean update(Town town) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                town);

        return jdbc.update("update town set name=:name where id=:id", params) == 1;
    }

    public boolean create(Town town) {

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                town);

        return jdbc
                .update("insert into town (name, code) values (:name, :country.code)",
                        params) == 1;
    }

    @Transactional
    public int[] create(List<Town> towns) {

        SqlParameterSource[] params = SqlParameterSourceUtils
                .createBatch(towns.toArray());

        return jdbc
                .batchUpdate("insert into town(name, code) values (:name, :country.code)", params);
    }

    /**
     * Delete specific item from table town
     * @param id town id
     * @return bool
     */
    public boolean delete(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbc.update("delete from town where id=:id", params) == 1;
    }

    /**
     * Delete all towns from table TOWN
     */
    public void deleteTowns() {
        jdbc.getJdbcOperations().execute("DELETE FROM town");
    }


    public Town getTown(int townId) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", townId);

        return jdbc.queryForObject("select * from town join country using (code) where id=:id", params,
                        new RowMapper<Town>() {

                            public Town mapRow(ResultSet rs, int rowNum)
                                    throws SQLException {

                                Country country = new Country();
                                country.setCode(rs.getInt("code"));
                                country.setTitle(rs.getString("title"));

                                Town town = new Town();
                                town.setId(rs.getInt("id"));
                                town.setName(rs.getString("name"));
                                town.setCountry(country);

                                return town;
                            }

                        });
    }
}
