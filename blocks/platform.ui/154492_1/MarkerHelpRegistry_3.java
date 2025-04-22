	private boolean hasResolution(IMarker marker, IConfigurationElement element, IMarkerResolutionGenerator generator) {
		if (generator == null || generator == GENERATOR_ERROR) {
			return false;
		}
		if (generator == GENERATOR_NOT_ACTIVE) {
			return true;
		}
		if (generator instanceof IMarkerResolutionGenerator2) {
			if (((IMarkerResolutionGenerator2) generator).hasResolutions(marker)) {
				return true;
			}
		} else {
			IMarkerResolution[] resolutions = generator.getResolutions(marker);
			if (resolutions == null) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
						IStatus.ERROR, "Failure in " + generator.getClass().getName() + //$NON-NLS-1$
								" from plugin " + element.getContributor().getName() + //$NON-NLS-1$
								": getResolutions(IMarker) must not return null", //$NON-NLS-1$
						null), StatusManager.LOG);

				return false;
			} else if (resolutions.length > 0) {
				return true;
			}
		}
		return false;
	}

	private IMarkerResolutionGenerator createGenerator(IConfigurationElement element) {
		IMarkerResolutionGenerator generator = getGenerator(element);
		if (generator != null) {
			return generator;
		}
		Bundle bundle = Platform.getBundle(element.getContributor().getName());
		if (bundle.getState() == Bundle.ACTIVE) {
