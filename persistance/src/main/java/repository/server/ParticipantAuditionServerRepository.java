package repository.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import model.server.Request;
import model.server.RequestType;
import model.server.Response;
import model.server.ResponseType;
import repository.IParticipantAuditionRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantAuditionServerRepository implements IParticipantAuditionRepository {
    Properties prop;
    Gson gson;

    public ParticipantAuditionServerRepository(Properties prop) {
        this.prop = prop;
        this.gson = new Gson();
    }

    @Override
    public void save(AuditionDTO entity) {
    }

    @Override
    public void delete(Integer integer) {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s. getOutputStream()));
            String requestBody = gson.toJson(integer);
            Request request = new Request(RequestType.RemoveAudition, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);

            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);

            if(response.getResponseType() == ResponseType.ERROR){
                System.out.println("delete error");
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AuditionDTO entity) {

    }

    @Override
    public AuditionDTO findOne(Integer integer) {
        return null;
    }

    @Override
    public List<AuditionDTO> findAll() {
        return null;
    }


    public List<AuditionDTO> findAll(int participantId) {
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            String requestBody = gson.toJson(participantId);
            Request request = new Request(RequestType.GetAuditionsForOneParticipant, requestBody);
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

    @Override
    public void save(Integer idParticipant, Integer idAudition) {
        List<Integer> ints = new ArrayList<>();
        ints.add(idParticipant);
        ints.add(idAudition);
        try {
            Socket s = ServerConnection.getSocketConnection(prop);
            sendNormalRequest(new DataOutputStream(s. getOutputStream()));
            String requestBody = gson.toJson(ints);
            Request request = new Request(RequestType.AddAudition, requestBody);
            String requestString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(requestString);

            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);

            if(response.getResponseType() == ResponseType.ERROR){
                System.out.println("save pa error");
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNormalRequest(DataOutputStream outputStream) throws IOException {
        Request request = new Request(RequestType.NormalRequest,"");
        outputStream.writeUTF(gson.toJson(request));
    }
}
