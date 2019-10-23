package model.participant;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ParticipantDTO  implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private List<AuditionDTO> auditionDTOList;

    public ParticipantDTO() {
    }

    public ParticipantDTO(Integer id, String name, Integer age, List<AuditionDTO> auditionDTOList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.auditionDTOList = auditionDTOList;
    }

    public ParticipantDTO(String name, Integer age, List<AuditionDTO> auditionDTOList) {
        this.name = name;
        this.age = age;
        this.auditionDTOList = auditionDTOList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<AuditionDTO> getAuditionDTOList() {
        return auditionDTOList;
    }

    public void setAuditionDTOList(List<AuditionDTO> auditionDTOList) {
        this.auditionDTOList = auditionDTOList;
    }

    @Override
    public String toString() {
        return "\nParticipantDTO: " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", " + auditionDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDTO that = (ParticipantDTO) o;
        return id == that.id &&
                age == that.age &&
                name.equals(that.name) &&
                Objects.equals(auditionDTOList, that.auditionDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, auditionDTOList);
    }
}
