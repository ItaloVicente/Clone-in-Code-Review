package org.eclipse.e4.ui.macros.internal;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.macros.EAcceptedCommands;

public class AcceptedCommandsImplementation implements EAcceptedCommands {

	@Inject
	public AcceptedCommandsImplementation(IEclipseContext eclipseContext) {
		Assert.isNotNull(eclipseContext);
		this.fEclipseContext = eclipseContext;
	}

	private Map<String, Boolean> fMacroAcceptedCommandIds;

	private IEclipseContext fEclipseContext;

	private Map<String, Boolean> getInternalAcceptedCommands() {
		if (fMacroAcceptedCommandIds == null) {
			fMacroAcceptedCommandIds = new HashMap<>();
			IExtensionRegistry registry = fEclipseContext.get(IExtensionRegistry.class);
			if (registry != null) {
				for (IConfigurationElement ce : registry
						.getConfigurationElementsFor("org.eclipse.e4.ui.macros.acceptedCommands")) { //$NON-NLS-1$
					if ("acceptedCommand".equals(ce.getName()) && ce.getAttribute("id") != null //$NON-NLS-1$ //$NON-NLS-2$
							&& ce.getAttribute("recordActivation") != null) { //$NON-NLS-1$
						Boolean recordActivation = Boolean.parseBoolean(ce.getAttribute("recordActivation")) //$NON-NLS-1$
								? Boolean.TRUE
								: Boolean.FALSE;
						fMacroAcceptedCommandIds.put(ce.getAttribute("id"), recordActivation); //$NON-NLS-1$
					}
				}
			}
		}
		return fMacroAcceptedCommandIds;
	}

	@Override
	public boolean isCommandAccepted(String commandId) {
		return getInternalAcceptedCommands().containsKey(commandId);
	}

	@Override
	public boolean isCommandRecorded(String commandId) {
		Map<String, Boolean> macroAcceptedCommands = getInternalAcceptedCommands();
		return macroAcceptedCommands.get(commandId);
	}

	@Override
	public void setCommandAccepted(String commandId, boolean acceptCommand, boolean recordActivation) {
		if (acceptCommand) {
			getInternalAcceptedCommands().put(commandId, recordActivation);
		} else {
			getInternalAcceptedCommands().remove(commandId);
		}
	}

	@Override
	public Map<String, Boolean> getCommandsAccepted() {
		Map<String, Boolean> internalAcceptedCommands = getInternalAcceptedCommands();
		return new HashMap<>(internalAcceptedCommands); // Create a copy
	}
}
