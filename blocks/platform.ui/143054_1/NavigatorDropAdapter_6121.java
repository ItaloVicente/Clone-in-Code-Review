	private IStatus error(String message) {
		return error(message, null);
	}

	private IStatus error(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message, exception);
	}

	private IContainer getActualTarget(IResource mouseTarget) {
		if (getFeedbackEnabled()) {
			if (getCurrentLocation() == LOCATION_BEFORE || getCurrentLocation() == LOCATION_AFTER) {
				return mouseTarget.getParent();
			}
		}
		if (mouseTarget.getType() == IResource.FILE) {
			return mouseTarget.getParent();
		}
		return (IContainer) mouseTarget;
	}

	private Display getDisplay() {
		return getViewer().getControl().getDisplay();
	}

	private IResource[] getSelectedResources() {
