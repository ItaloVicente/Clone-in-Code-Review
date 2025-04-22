	@Override
	public Saveable[] getSaveables() {
		if (!hasSaveablesProvider()) {
			IEditorPart saveablePart = getSite().getPage().getActiveEditor();
			return saveablePart instanceof ISaveablesSource ? ((ISaveablesSource) saveablePart).getSaveables()
					: new Saveable[] { new DefaultSaveable(saveablePart) };
		}
		return super.getSaveables();
	}

	@Override
	public Saveable[] getActiveSaveables() {
		if (!hasSaveablesProvider()) {
			IEditorPart saveablePart = getSite().getPage().getActiveEditor();
			return saveablePart instanceof ISaveablesSource ? ((ISaveablesSource) saveablePart).getActiveSaveables()
					: new Saveable[] { new DefaultSaveable(saveablePart) };
		}
		return super.getActiveSaveables();
	}
