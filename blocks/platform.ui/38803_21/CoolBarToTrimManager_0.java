	public static String getToolbarLabel(MApplication application, MUIElement elt) {
		String name = getTransientName(elt);
		if (name != null) {
			return name;
		}
		String elementId = elt.getElementId();
		return getToolbarLabel(application, elementId);
	}

	private static String getToolbarLabel(MApplication application, String elementId) {
		String name;
		if (IWorkbenchActionConstants.TOOLBAR_FILE.equalsIgnoreCase(elementId)) {
			return WorkbenchMessages.WorkbenchWindow_FileToolbar;
		}
		if (IWorkbenchActionConstants.TOOLBAR_NAVIGATE.equalsIgnoreCase(elementId)) {
			return WorkbenchMessages.WorkbenchWindow_NavigateToolbar;
		}
		if (IWorkbenchActionConstants.TOOLBAR_HELP.equalsIgnoreCase(elementId)) {
			return WorkbenchMessages.WorkbenchWindow_HelpToolbar;
		}
		List<MTrimContribution> trimContributions = application.getTrimContributions();
		for (MTrimContribution mtb : trimContributions) {
			for (MTrimElement e : mtb.getChildren()) {
				if (e.getElementId().equals(elementId)) {
					name = getTransientName(e);
					if (name != null) {
						return name;
					}
				}
			}
		}
		return null;
	}

	static String getTransientName(MUIElement elt) {
		Object name = elt.getTransientData().get("Name"); //$NON-NLS-1$
		if (name instanceof String) {
			return (String) name;
		}
		return null;
	}

