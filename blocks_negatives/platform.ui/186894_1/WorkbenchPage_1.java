		MPerspective perspective = getPerspectiveStack().getSelectedElement();
		return ModeledPageLayout.getIds(perspective, ModeledPageLayout.SHOW_IN_PART_TAG);
	}

	/** @return showInId configured by perspective or null if not configured **/
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
