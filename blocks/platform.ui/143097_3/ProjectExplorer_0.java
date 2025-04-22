	@Override
	public Saveable[] getSaveables() {
		Saveable[] saveables = super.getSaveables();
		if (saveables == null || saveables.length == 0) {
			ISaveablePart saveablePart = getSite().getPage().getActiveEditor();
			if (saveablePart instanceof ISaveablesSource) {
				return ((ISaveablesSource) saveablePart).getSaveables();
			}
		}
		return saveables;
	}

	@Override
	public Saveable[] getActiveSaveables() {
		Saveable[] activeSaveables = super.getActiveSaveables();
		if (activeSaveables == null || activeSaveables.length == 0) {
			ISaveablePart saveablePart = getSite().getPage().getActiveEditor();
			if (saveablePart instanceof ISaveablesSource) {
				return ((ISaveablesSource) saveablePart).getActiveSaveables();
			}
		}
		return activeSaveables;
	}
