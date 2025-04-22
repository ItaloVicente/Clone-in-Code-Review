
package org.eclipse.ui.internal.registry;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

public final class ViewParameterValues implements IParameterValues {

	@Override
	public final Map getParameterValues() {
		final Map values = new HashMap();

		final IViewDescriptor[] views = PlatformUI.getWorkbench()
				.getViewRegistry().getViews();
		for (int i = 0; i < views.length; i++) {
			final IViewDescriptor view = views[i];
			values.put(view.getLabel(), view.getId());
		}

		return values;
	}
}
