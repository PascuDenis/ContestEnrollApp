package service;

import model.participant.ParticipantDTO;

import javax.xml.bind.ValidationException;

public class Validator{
    void validate(ParticipantDTO entity) throws ValidationException{
        StringBuffer message = new StringBuffer();
        if (entity.getId() <= 0)
            message.append("The ID cannot be negative or zero");
        if ((entity.getAge() <5) || (entity.getAge() > 90))
            message.append("The age must be between 5 and 90");
        if (message.length()>0)
            throw new ValidationException(message.toString());
    };
}
