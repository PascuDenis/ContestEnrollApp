package repository.utils;

import java.rmi.RemoteException;

public interface IObservable {
    void add(IObserver o);

    void remove(IObserver o);

    void notifyObservers() throws RemoteException;
}
