	protected void saveValues(IMemento memento) {
		memento.putString(IWorkbenchConstants.TAG_LABEL, getLabel());
		memento.putString(IWorkbenchConstants.TAG_IMAGE, getImageFilename());
		memento.putString(IWorkbenchConstants.TAG_CLASS, getClassName());
		memento.putString(IWorkbenchConstants.TAG_LAUNCHER, getLauncher());
		memento.putString(IWorkbenchConstants.TAG_FILE, getFileName());
		memento.putString(IWorkbenchConstants.TAG_ID, getId());
		memento.putString(IWorkbenchConstants.TAG_PLUGIN, getPluginId());

		memento.putInteger(IWorkbenchConstants.TAG_OPEN_MODE, getOpenMode());
		memento.putString(IWorkbenchConstants.TAG_INTERNAL, String.valueOf(isInternal()));
		memento.putString(IWorkbenchConstants.TAG_OPEN_IN_PLACE, String.valueOf(isOpenInPlace()));

		if (this.program != null) {
			memento.putString(IWorkbenchConstants.TAG_PROGRAM_NAME, this.program.getName());
		}
	}

