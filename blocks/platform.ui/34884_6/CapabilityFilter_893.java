package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.internal.util.Util;

public class AdaptableForwarder implements IAdaptable {

	private Object element;
	
	public AdaptableForwarder(Object element) {
		this.element = element;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
        return Util.getAdapter(element, adapter);
	}
}
