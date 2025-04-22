	@Override
	public Saveable[] getSaveables() {
		if (!hasSaveablesProvider()) {
			IEditorPart saveablePart = getSite().getPage().getActiveEditor();
			return saveablePart != null
					? saveablePart instanceof ISaveablesSource ? ((ISaveablesSource) saveablePart).getSaveables()
							: new Saveable[] { new DefaultSaveable(saveablePart) }
					: new Saveable[] {};
		}
		return super.getSaveables();
	}

	@Override
	public Saveable[] getActiveSaveables() {
		if (!hasSaveablesProvider()) {
			IEditorPart saveablePart = getSite().getPage().getActiveEditor();
			return saveablePart != null
					? saveablePart instanceof ISaveablesSource ? ((ISaveablesSource) saveablePart).getActiveSaveables()
							: new Saveable[] { new DefaultSaveable(saveablePart) }
					: new Saveable[] {};
		}
		return super.getActiveSaveables();
	}
