package org.eclipse.ui.navigator;

import org.eclipse.ui.IMemento;

public interface IMementoAware {

	public void restoreState(IMemento aMemento);

	public void saveState(IMemento aMemento);

}
