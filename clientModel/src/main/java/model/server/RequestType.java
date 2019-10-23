package model.server;

public enum RequestType {
    LOGIN, LOGOUT, GetAllParticipants, GetOneParticipant, GetAuditionsForOneParticipant, GetNumberOfParticipantsForOneAudition, AddParticipant, AddAudition, RemoveParticipant, RemoveAudition, UpdateParticipant, NormalRequest, ObserverRequest, Update
}
