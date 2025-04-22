package org.eclipse.ui.tests.themes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class ThemePropertyListener implements IPropertyChangeListener {

    private ArrayList events = new ArrayList();

    @Override
	public void propertyChange(PropertyChangeEvent event) {
        events.add(event);
    }

    public List getEvents() {
        return events;
    }
}
