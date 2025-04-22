	private Map<String, Boolean> getMacroAcceptedCommands() {
		if (fMacroAcceptedCommandIds == null) {
			fMacroAcceptedCommandIds = new HashMap<>();
			IExtensionRegistry registry = context.get(IExtensionRegistry.class);
			for (IConfigurationElement ce : registry
					.getConfigurationElementsFor("org.eclipse.e4.core.macros.commands")) { //$NON-NLS-1$
				if ("accept".equals(ce.getName()) && ce.getAttribute("id") != null //$NON-NLS-1$ //$NON-NLS-2$
						&& ce.getAttribute("recordActivation") != null) { //$NON-NLS-1$
					Boolean recordActivation = Boolean.parseBoolean(ce.getAttribute("recordActivation")) ? Boolean.TRUE //$NON-NLS-1$
							: Boolean.FALSE;
					fMacroAcceptedCommandIds.put(ce.getAttribute("id"), recordActivation); //$NON-NLS-1$
				}
			}
		}
		return fMacroAcceptedCommandIds;
	}

