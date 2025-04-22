package org.eclipse.ui;

public interface INavigationHistory {
    public void markLocation(IEditorPart part);

    public INavigationLocation getCurrentLocation();

    public INavigationLocation[] getLocations();
}
