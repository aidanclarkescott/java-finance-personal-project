package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an observer to the list of observers
    public void addObserver(Observer o) {
        if (!observers.contains(o)) {
            this.observers.add(o);
        }
    }

    // EFFECTS: calls the update method on all observers in observer list.
    public void notifyObservers(double value) {
        for (Observer o : observers) {
            o.update(value);
        }
    }
}
