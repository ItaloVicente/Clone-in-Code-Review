			if (saveable == null || !saveable.isDirty()) {
				continue;
			}
			IWorkbenchPart previousPart = saveables.get(saveable);
			if (previousPart != null) {
				if (previousPart == saveable) {
					continue;
				}
				if (part != saveable && previousPart instanceof IEditorPart) {
					continue;
