package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {

    private Object state;

    private List views = new ArrayList(1);

    public Model(Object initialState) {
        state = initialState;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object newState, IChangeListener toOmit) {
        if (areEqual(newState, state)) {
            return;
        }

        state = newState;

        Iterator iter = views.iterator();
        while (iter.hasNext()) {
            IChangeListener next = (IChangeListener) iter.next();

            if (next != toOmit) {
                next.update(true);
            }
        }
    }

    private boolean areEqual(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

    public void addChangeListener(IChangeListener changeListener) {
        changeListener.update(false);
        views.add(changeListener);
    }

    public void removeChangeListener(IChangeListener changeListener) {
        views.remove(changeListener);
    }

}
