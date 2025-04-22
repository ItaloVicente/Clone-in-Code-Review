package org.eclipse.ui;

public interface IViewLayout {

    public boolean isCloseable();

    public void setCloseable(boolean closeable);

    public boolean isMoveable();

    public void setMoveable(boolean moveable);

    public boolean isStandalone();

    public boolean getShowTitle();
}
