package dto;

import java.io.Serializable;
import java.util.List;

public class ParticipantDTO implements Serializable {
    private int id;
    private String name;
    private int age;
    private List<AuditionDTO> auditionList;

    public ParticipantDTO(int id, String name, int age, List<AuditionDTO> auditionList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.auditionList = auditionList;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<AuditionDTO> getAuditionList() {
        return auditionList;
    }

    public void setAuditionList(List<AuditionDTO> auditionList) {
        this.auditionList = auditionList;
    }

    @Override
    public String toString() {
        return "ParticipantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", auditionList=" + auditionList +
                '}';
    }
}
