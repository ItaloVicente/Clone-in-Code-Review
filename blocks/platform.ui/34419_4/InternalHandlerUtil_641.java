package org.eclipse.ui.internal;

public class IntModel extends Model {
    public IntModel(int initialValue) {
        super(new Integer(initialValue));
    }

    public void set(int newValue, IChangeListener source) {
        setState(new Integer(newValue), source);
    }

    public void set(int newValue) {
        setState(new Integer(newValue), null);
    }

    public int get() {
        return ((Integer) getState()).intValue();
    }
}
