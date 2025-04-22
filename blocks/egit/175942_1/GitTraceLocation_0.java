package org.eclipse.egit.core.internal.trace;

import org.eclipse.egit.core.Activator;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugOptionsListener;
import org.osgi.service.component.annotations.Component;

@Component(property = DebugOptions.LISTENER_SYMBOLICNAME + '='
		+ Activator.PLUGIN_ID)
public class DebugOptionsHandler implements DebugOptionsListener {

	@Override
	public void optionsChanged(DebugOptions options) {
		GitTraceLocation.initializeFromOptions(options);
	}
}
