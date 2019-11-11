package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer o) {
        if (!observers.contains(o)) {
            this.observers.add(o);
        }
    }

    public void notifyObservers(double value) {
        for (Observer o : observers) {
            o.update(value);
        }
    }
}
