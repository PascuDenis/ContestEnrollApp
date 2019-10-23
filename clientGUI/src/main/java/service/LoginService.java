package service;

import com.google.gson.Gson;
import model.server.Request;
import model.server.RequestType;
import model.server.Response;
import model.server.ResponseType;
import model.user.UserDTO;
import repository.server.ServerConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class LoginService {
    private Properties properties;
    private Gson gson;

    public LoginService(Properties properties) {
        this.properties = properties;
        this.gson = new Gson();
    }

    public  boolean t(){
        return true;
    }

    public boolean login(String username, String password){
        try{
            Socket s = ServerConnection.getSocketConnection(properties);
            sendNormalRequest(new DataOutputStream(s.getOutputStream()));
            UserDTO user = new UserDTO(username, password);
            String requestString = gson.toJson(user);
            Request request = new Request(RequestType.LOGIN,requestString);
            String transferingString = gson.toJson(request);
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(transferingString);
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            String responseString = inputStream.readUTF();
            Response response = gson.fromJson(responseString, Response.class);
            s.close();
            if(response.getResponseType() == ResponseType.OK)
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    private void sendNormalRequest(DataOutputStream outputStream) throws IOException {
        Request request = new Request(RequestType.NormalRequest,"");
        outputStream.writeUTF(gson.toJson(request));
    }
}
