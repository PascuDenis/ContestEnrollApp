package repository.utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void update() throws RemoteException;
}
