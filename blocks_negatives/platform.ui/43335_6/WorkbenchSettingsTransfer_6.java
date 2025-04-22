	/**
	 * Return a status message for missing workspace settings.
	 * @return IStatus
	 */
	protected IStatus noWorkingSettingsStatus() {
		return new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH,
				WorkbenchMessages.WorkbenchSettings_CouldNotFindLocation);
