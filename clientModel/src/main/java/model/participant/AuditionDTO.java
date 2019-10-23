package model.participant;

import java.io.Serializable;
import java.util.Objects;

public class AuditionDTO implements Serializable {
    private Integer id;
    private Types typ;
    private Distance distance;

    public AuditionDTO() {
    }

    public AuditionDTO(Integer id, Types typ, Distance distance) {
        this.id = id;
        this.typ = typ;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Types getTyp() {
        return typ;
    }

    public void setTyp(Types typ) {
        this.typ = typ;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return  "" + typ + '\'' +
                ", d: '" + distance + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditionDTO auditionDTO = (AuditionDTO) o;
        return  typ == auditionDTO.typ &&
                distance == auditionDTO.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typ, distance);
    }
}
