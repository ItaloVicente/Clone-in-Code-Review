	private IWorkbenchPart getWrappedPart(CompatibilityPart comPart) {
		IWorkbenchPart part = comPart.getPart();
		if (part == null) {
			WorkbenchPlugin.log(new RuntimeException("Trying to access already disposed part: " //$NON-NLS-1$
					+ comPart));
		}
		return part;
	}

