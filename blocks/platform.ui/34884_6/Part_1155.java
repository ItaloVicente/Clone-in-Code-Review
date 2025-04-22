package org.eclipse.ui.internal.part;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistable;

public class NullPersistable implements IPersistable {

    @Override
	public void saveState(IMemento memento) {
    }

}
