package org.eclipse.ui.views;

public interface IStickyViewDescriptor {
    public String getId();

    public int getLocation();

    public boolean isCloseable();

    public boolean isMoveable();
}
