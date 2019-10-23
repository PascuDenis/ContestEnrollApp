package repository.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AbstractIObservable implements IObservable {
    protected ArrayList<IObserver> IObserverList = new ArrayList<>();

    @Override
    public void add(IObserver o) {
        IObserverList.add(o);
    }

    @Override
    public void remove(IObserver o) {
        IObserverList.remove(o);
    }

    @Override
    public void notifyObservers() throws RemoteException {
        for (IObserver o : IObserverList){
            o.update();
        }
    }
}
