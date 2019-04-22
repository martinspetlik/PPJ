package tul.semestralka.data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import java.util.List;


@Entity
@Table(name = "country")
public class Country{

    @Column(name ="title")
    private String title;

    @Id
    @Column(name ="code")
    private String code;

    public Country() {

    }

    public Country(String title) {
        this.title = title;
        this.code = this.getCodeFromTitle(title);
    }

    public Country(String title, String code) {
        this.title = title;

        if (code.equals(this.getCodeFromTitle(title))){
            this.code = code;
        } else {

            System.out.println("Country code is not valid ISO 3166 code " + code);
            System.out.println("ISO code " + this.getCodeFromTitle(title));
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (!code.equals(getCodeFromTitle(title))) {
            code = getCodeFromTitle(title);
        }
        this.code = code;
    }

    public void generateCode() {
        this.code = getCodeFromTitle(title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Country other = (Country) obj;

        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Country [title=" + title + ", code=" + code + "]";
    }

    public String getCodeFromTitle(String title)
    {
        List<CountryCode> possibleCodes = CountryCode.findByName(title);

        if (possibleCodes.size() > 0) {
            return possibleCodes.get(0).toString().toLowerCase();
        }
        else {
            return null;
        }
    }
}
