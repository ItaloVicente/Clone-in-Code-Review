package org.eclipse.ui;

public interface INavigationLocation {

    public void dispose();

    public void releaseState();

    public void saveState(IMemento memento);

    public void restoreState(IMemento memento);

    public void restoreLocation();

    public boolean mergeInto(INavigationLocation currentLocation);

    public Object getInput();

    public String getText();

    public void setInput(Object input);

    public void update();
}
