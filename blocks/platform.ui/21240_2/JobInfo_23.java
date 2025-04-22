package org.eclipse.e4.ui.progress.internal;

public interface IProgressUpdateCollector {

    void refresh();

    void refresh(Object[] elements);

    void add(Object[] elements);

    void remove(Object[] elements);

}
