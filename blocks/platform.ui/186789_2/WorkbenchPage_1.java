	public String getShowInId() {
		String perspectiveId = getPerspective().getId();
		String showInIdFromRegistry = getShowInIdFromRegistry(perspectiveId);
		return showInIdFromRegistry;
	}

	public static String getShowInIdFromRegistry(String perspectiveId) {
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_PERSPECTIVES);
		if (point == null) {
			return null;
		}
		IExtension[] extensions = point.getExtensions();
		if (extensions == null) {
			return null;
		}
		for (IExtension extension : extensions) {
			IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement configElement : configElements) {
				String id = configElement.getAttribute("id"); //$NON-NLS-1$
				if (Objects.equals(perspectiveId, id)) {
					String showInId = configElement.getAttribute("showInId"); //$NON-NLS-1$
					return showInId;
				}
			}
		}
		return null;
	}
