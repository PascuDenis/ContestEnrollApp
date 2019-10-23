package dto;

import model.participant.Types;
import model.participant.Distance;

import java.io.Serializable;

public class AuditionDTO implements Serializable {
    private int id;
    private Types typ;
    private Distance distance;

    public AuditionDTO(int id, Types typ, Distance distance) {
        this.id = id;
        this.typ = typ;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "AuditionDTO{" +
                "id=" + id +
                ", typ=" + typ +
                ", distance=" + distance +
                '}';
    }
}
