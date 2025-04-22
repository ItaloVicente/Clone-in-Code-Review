	@Override
	public Saveable[] getSaveables() {
		if (!hasSaveablesProvider()) {
			ISaveablePart saveablePart = getSite().getPage().getActiveEditor();
			if (saveablePart instanceof ISaveablesSource) {
				return ((ISaveablesSource) saveablePart).getSaveables();
			}
		}
		return super.getSaveables();
	}

	@Override
	public Saveable[] getActiveSaveables() {
		if (!hasSaveablesProvider()) {
			ISaveablePart saveablePart = getSite().getPage().getActiveEditor();
			if (saveablePart instanceof ISaveablesSource) {
				return ((ISaveablesSource) saveablePart).getActiveSaveables();
			}
		}
		return super.getActiveSaveables();
	}
