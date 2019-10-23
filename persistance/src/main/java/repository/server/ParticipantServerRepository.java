package repository.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import model.server.Request;
import model.server.RequestType;
import model.server.Response;
import model.server.ResponseType;
import repository.IParticipantRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantServerRepository implements IParticipantRepository {
    Properties prop;
    Gson gson;

    public ParticipantServerRepository(Properties prop) {
        this.prop = prop;
        this.gson = new Gson();
    }

    @Override
    public List<AuditionDTO> getAuditionsForOneParticipant(Integer id) {
        return null;
    }
    public List<AuditionDTO> getAuditionsForOneParticipant(ParticipantDTO participantDTO) {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            String requestBody = gson.toJson(participantDTO);
            Request request = new Request(RequestType.GetAuditionsForOneParticipant, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);
            if(response.getResponseType().equals(ResponseType.OK)){
                Type collectionType = new TypeToken<ArrayList<AuditionDTO>>(){}.getType();
                return gson.fromJson(response.getResponseBody(),collectionType);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Integer countNumberOfParticipantForOneAudition(AuditionDTO audition) {
        Integer count = 0;
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            String requestBody = gson.toJson(audition);
            Request request = new Request(RequestType.GetNumberOfParticipantsForOneAudition, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);
            if(response.getResponseType().equals(ResponseType.OK)){
                count = gson.fromJson(response.getResponseBody(), (Type) Integer.class);
            }
            s.close();
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void save(ParticipantDTO entity) {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s. getOutputStream()));
            String requestBody = gson.toJson(entity);
            Request request = new Request(RequestType.AddAudition, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);

            if(response.getResponseType() == ResponseType.OK){
                System.out.println("add successful");
            }
            else if(response.getResponseType() == ResponseType.ERROR){
                System.out.println("add error");
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer integer) {
        try{
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            String requestBody = gson.toJson(new ParticipantDTO(integer, null, null, null));
            Request request = new Request(RequestType.RemoveParticipant, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(requestString, Response.class);
            if(response.getResponseType() == ResponseType.OK){
                System.out.println("delete successful");
            }
            else if (response.getResponseType() == ResponseType.ERROR){
                System.out.println("delete error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ParticipantDTO entity) {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s. getOutputStream()));
            String requestBody = gson.toJson(entity);
            Request request = new Request(RequestType.UpdateParticipant, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);

            if(response.getResponseType() == ResponseType.OK){
                System.out.println("update successful");
            }
            else if(response.getResponseType() == ResponseType.ERROR){
                System.out.println("update error");
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ParticipantDTO findOne(Integer integer) {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            String requestBody = gson.toJson(new ParticipantDTO(integer, null, null, null));
            Request request = new Request(RequestType.GetOneParticipant, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);
            if(response.getResponseType().equals(ResponseType.OK)){
                return gson.fromJson(response.getResponseBody(),ParticipantDTO.class);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ParticipantDTO> findAll() {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            Request request = new Request(RequestType.GetAllParticipants, "");
            String transferString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(transferString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);
            if(response.getResponseType().equals(ResponseType.OK)){
                Type collectionType = new TypeToken<ArrayList<ParticipantDTO>>(){}.getType();
                return gson.fromJson(response.getResponseBody(),collectionType);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendNormalRequest(DataOutputStream outputStream) throws IOException {
        Request request = new Request(RequestType.NormalRequest,"");
        outputStream.writeUTF(gson.toJson(request));
    }
}
