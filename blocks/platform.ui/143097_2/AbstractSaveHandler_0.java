	private ISaveablePart getSaveablePart(IWorkbenchPart activePart) {
		ISaveablePart part = SaveableHelper.getSaveable(activePart);
		if (!(part instanceof IEditorPart) && part instanceof ISaveablesSource) {
			ISaveablesSource modelSource = (ISaveablesSource) part;
			Saveable[] saveables = modelSource.getSaveables();
			if (saveables == null || saveables.length == 0) {
				return null;
			}
		}
		return part;
	}
