package org.eclipse.ui.internal.progress;

public interface IProgressUpdateCollector {

    void refresh();

    void refresh(Object[] elements);

    void add(Object[] elements);

    void remove(Object[] elements);

}
