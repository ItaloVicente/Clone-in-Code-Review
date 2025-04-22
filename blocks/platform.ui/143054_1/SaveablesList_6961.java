	public Object preCloseParts(List<IWorkbenchPart> partsToClose, boolean addNonPartSources, boolean save,
			final IWorkbenchWindow window, Map<Saveable, Save> saveOptions) {
		if (saveOptions == null || saveOptions.isEmpty()) {
			preCloseParts(partsToClose, save, window);
		}
		Collection<Save> saveValues = saveOptions.values();
		for (Save decision : saveValues) {
			if (decision == Save.CANCEL) {
				return false;
			}
		}
		return preCloseParts(partsToClose, addNonPartSources, save, window, window, saveOptions);
	}

	public Object preCloseParts(List<IWorkbenchPart> partsToClose, boolean save, final IWorkbenchWindow window) {
