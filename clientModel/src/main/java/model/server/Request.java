package model.server;

public class Request {
    private RequestType requestType;

    private String requestBody;

    public Request(RequestType requestType, String requestBody){
        this.requestType = requestType;
        this.requestBody = requestBody;
    }

    public RequestType getRequestType() {
        return requestType;
    }

        public String getRequestBody(){
        return requestBody;
    }
}

//    private List<ParticipantDTO> participantDTOList;
//    public RequestType getRequestType(){
//        return requestType;
//    }
//
//    public List<ParticipantDTO> getParticipantDTOList() {
//        return participantDTOList;
//    }
