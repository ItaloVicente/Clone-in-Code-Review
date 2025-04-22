
package org.eclipse.ui.internal.registry;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;

public final class PerspectiveParameterValues implements IParameterValues {

	@Override
	public final Map getParameterValues() {
		final Map values = new HashMap();

		final IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().getPerspectives();
		for (int i = 0; i < perspectives.length; i++) {
			final IPerspectiveDescriptor perspective = perspectives[i];
			values.put(perspective.getLabel(), perspective.getId());
		}

		return values;
	}
}
