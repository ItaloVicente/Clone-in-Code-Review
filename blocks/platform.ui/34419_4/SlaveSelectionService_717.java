
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.services.IDisposable;

public class SlavePartService implements IPartService, IDisposable {

	private IPartService parent;

	private ListenerList listeners = new ListenerList(ListenerList.IDENTITY);

	public SlavePartService(IPartService parentPartService) {
		if (parentPartService == null) {
			throw new IllegalArgumentException(
					"The parent part service cannot be null"); //$NON-NLS-1$
		}
		this.parent = parentPartService;
	}

	@Override
	public void addPartListener(IPartListener listener) {
		listeners.add(listener);
		parent.addPartListener(listener);
	}

	@Override
	public void addPartListener(IPartListener2 listener) {
		listeners.add(listener);
		parent.addPartListener(listener);
	}

	@Override
	public IWorkbenchPart getActivePart() {
		return parent.getActivePart();
	}

	@Override
	public IWorkbenchPartReference getActivePartReference() {
		return parent.getActivePartReference();
	}

	@Override
	public void removePartListener(IPartListener listener) {
		listeners.remove(listener);
		parent.removePartListener(listener);
	}

	@Override
	public void removePartListener(IPartListener2 listener) {
		listeners.remove(listener);
		parent.removePartListener(listener);
	}

	@Override
	public void dispose() {
		Object list[] = listeners.getListeners();
		for (int i = 0; i < list.length; i++) {
			Object obj = list[i];
			if (obj instanceof IPartListener) {
				parent.removePartListener((IPartListener) obj);
			}
			if (obj instanceof IPartListener2) {
				parent.removePartListener((IPartListener2) obj);
			}
		}
		listeners.clear();
	}

}
