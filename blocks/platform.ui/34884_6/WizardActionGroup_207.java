
package org.eclipse.ui.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.SaveablesLifecycleEvent;
import org.eclipse.ui.services.IDisposable;

public abstract class SaveablesProvider implements IDisposable {

	private ISaveablesLifecycleListener listener;

	protected SaveablesProvider() {
	}

	final public void init(ISaveablesLifecycleListener listener) {
		this.listener = listener;
		doInit();
	}

	protected void doInit() {
	}

	final protected void fireSaveablesOpened(Saveable[] models) {
		listener.handleLifecycleEvent(new SaveablesLifecycleEvent(this,
				SaveablesLifecycleEvent.POST_OPEN, models, false));
	}

	final protected boolean fireSaveablesClosing(Saveable[] models,
			boolean force) {
		SaveablesLifecycleEvent saveablesLifecycleEvent = new SaveablesLifecycleEvent(
				this, SaveablesLifecycleEvent.PRE_CLOSE, models, force);
		listener.handleLifecycleEvent(saveablesLifecycleEvent);
		return saveablesLifecycleEvent.isVeto();
	}

	final protected void fireSaveablesClosed(Saveable[] models) {
		listener.handleLifecycleEvent(new SaveablesLifecycleEvent(this,
				SaveablesLifecycleEvent.POST_CLOSE, models, false));
	}

	final protected void fireSaveablesDirtyChanged(Saveable[] models) {
		listener.handleLifecycleEvent(new SaveablesLifecycleEvent(this,
				SaveablesLifecycleEvent.DIRTY_CHANGED, models, false));
	}

	public abstract Saveable[] getSaveables();

	public abstract Object[] getElements(Saveable saveable);

	public abstract Saveable getSaveable(Object element);

	@Override
	public void dispose() {
		listener = null;
	}

}
