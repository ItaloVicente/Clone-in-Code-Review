package org.eclipse.ui.examples.jobs.views;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;

public class ProgressExampleAdapterFactory implements IAdapterFactory {
	private SlowElementAdapter slowElementAdapter = new SlowElementAdapter();

	public Object getAdapter(Object object, Class type) {
		if(object instanceof SlowElement) {
			if(type == SlowElement.class || type == IDeferredWorkbenchAdapter.class || type == IWorkbenchAdapter.class)
				return slowElementAdapter;
		}
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] {
				SlowElement.class, IDeferredWorkbenchAdapter.class, IWorkbenchAdapter.class
				};
	}
}
