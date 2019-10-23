import com.google.gson.Gson;
import model.participant.AuditionDTO;
import model.participant.ParticipantDTO;
import model.server.Request;
import model.server.RequestType;
import model.server.Response;
import model.server.ResponseType;
import model.user.UserDTO;
import repository.utils.CommonUtils;
import repository.utils.IObserver;
import service.ServerService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class RequestWorker implements Runnable, IObserver {
    private Socket socket;
    private Gson gson;
    private ServerService serverService;
    private Boolean isObserver;
    private ArrayList<IObserver> IObservers;

    RequestWorker(Socket socket, Boolean isObserver, ArrayList<IObserver> IObservers) {
        this.socket = socket;
        this.gson = new Gson();
        this.serverService = CommonUtils.getFactory().getBean(ServerService.class);
        this.isObserver = isObserver;
        this.IObservers = IObservers;
    }

    private void handleLoginRequest(String requestBody) {
        try {
            UserDTO user = gson.fromJson(requestBody, UserDTO.class);
            boolean loginResult = serverService.login(user, null);
            Response response;
            if (loginResult) {
                response = new Response(ResponseType.OK, "");
            } else {
                response = new Response(ResponseType.BadLogin, "");
            }
            String responseString = gson.toJson(response);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetAllParticipantsRequest() {
        try {
            String responseBody = gson.toJson(serverService.getAllParticipants());
            String responseString = gson.toJson(new Response(ResponseType.OK, responseBody));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetAuditionsForOneParticipant(String requestBody) {
        try {
            ParticipantDTO participantDTO = gson.fromJson(requestBody, ParticipantDTO.class);
            String responseBody = gson.toJson(serverService.getAuditionListForOneParticipant(participantDTO));
            String responseString = gson.toJson(new Response(ResponseType.OK, responseBody));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetNumberOfParticipantsForOneAuditon(String requestBody) {
        try {
            AuditionDTO auditionDTO = gson.fromJson(requestBody, AuditionDTO.class);
            String resposeBody = gson.toJson(serverService.countNumberOfParticipantForOneAudition(auditionDTO));
            String responseString = gson.toJson(new Response(ResponseType.OK, resposeBody));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddParticipant(String requestBody) {
        try {
            System.out.println("hahhahahahahhaahahaahhaaahahahaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ParticipantDTO participantDTO = gson.fromJson(requestBody, ParticipantDTO.class);
            serverService.save(participantDTO);
            String responseString = gson.toJson(new Response(ResponseType.OK, ""));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);

            for (IObserver o : IObservers) {
                o.update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveParticipant(String requestBody) {
        try {
            ParticipantDTO participantDTO = gson.fromJson(requestBody, ParticipantDTO.class);
            serverService.delete(participantDTO.getId());
            String responseString = gson.toJson(new Response(ResponseType.OK, ""));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);

            for (IObserver o : IObservers) {
                o.update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateParticipant(String requestBody) {
        try {
            ParticipantDTO participantDTO = gson.fromJson(requestBody, ParticipantDTO.class);
            serverService.update(participantDTO);
            String responseString = gson.toJson(new Response(ResponseType.OK, ""));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(responseString);

            for (IObserver o : IObservers) {
                o.update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            if (isObserver) {
                waitForUpdate();
            }
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String stringRequest = in.readUTF();
            Request request = gson.fromJson(stringRequest, Request.class);
            switch (request.getRequestType()) {
                case LOGIN: {
                    handleLoginRequest(request.getRequestBody());
                    break;
                }
                case GetAllParticipants: {
                    handleGetAllParticipantsRequest();
                    break;
                }
                case GetAuditionsForOneParticipant: {
                    handleGetAuditionsForOneParticipant(request.getRequestBody());
                    break;
                }
                case GetNumberOfParticipantsForOneAudition: {
                    handleGetNumberOfParticipantsForOneAuditon(request.getRequestBody());
                    break;
                }
                case AddAudition: {
                    handleAddParticipant(request.getRequestBody());
                    break;
                }
                case RemoveParticipant: {
                    handleRemoveParticipant(request.getRequestBody());
                    break;
                }
                case UpdateParticipant: {
                    handleUpdateParticipant(request.getRequestBody());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForUpdate() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {
        try {
            String requestString = gson.toJson(new Request(RequestType.Update, ""));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
