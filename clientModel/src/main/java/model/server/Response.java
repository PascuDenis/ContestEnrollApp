package model.server;

import model.participant.ParticipantDTO;

import java.util.List;

public class Response {
    private ResponseType responseType;
    private String responseBody;

    public Response(ResponseType responseType, String responseBody) {
        this.responseType = responseType;
        this.responseBody = responseBody;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}


//    private List<ParticipantDTO> participantDTOList;
//    public List<ParticipantDTO> getParticipantDTOList() {
//        return participantDTOList;
//    }
