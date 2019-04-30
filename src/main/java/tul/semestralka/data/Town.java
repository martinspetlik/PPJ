package tul.semestralka.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "town")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 1, max = 100)
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "code")
    private Country country;

    @Transient
    @JsonIgnore
    private Weather lastWeather;

    public Town(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Town() {
    }

    public Town(int id, Country country, String name) {
        this.id = id;
        this.country = country;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setLastWeather(Weather lastWeather) {
        this.lastWeather = lastWeather;
    }

    public Weather getLastWeather() {
        return lastWeather;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            throw new NullPointerException("Object is null");
        if (getClass() != obj.getClass())
            return false;
        Town other = (Town) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Town [id=" + id + ", name=" + name + ", country=" + country + "]";
    }

}
