	public Object preCloseParts(List<IWorkbenchPart> partsToClose, boolean save, final IWorkbenchWindow window,
			Map<Saveable, Save> saveOptions) {
		if (saveOptions == null || saveOptions.size() == 0) {
			preCloseParts(partsToClose, save, window);
		}
		Collection<Save> saveValues = saveOptions.values();
		for (Save decision : saveValues) {
			if (decision == Save.CANCEL) {
				return false;
			}
		}
		return preCloseParts(partsToClose, false, save, window, window, saveOptions);
	}

