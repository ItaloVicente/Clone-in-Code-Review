package org.eclipse.ui.internal.preferences;

public interface IPropertyMapListener {
    public void propertyChanged(String[] propertyIds);
    public void listenerAttached();
}
