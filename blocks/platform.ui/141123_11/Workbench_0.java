	private boolean saveAllParts(boolean confirm, boolean closing) {
		EPartService partService = e4Context.get(EPartService.class);
		if (partService != null) {
			Collection<MPart> parts = getDirtyMParts();
			if (parts != null && parts.size() > 0) {
				MPart selected = null;
				for (MPart part : parts) {
					selected = part;
					break;
				}
				EModelService modelService = e4Context.get(EModelService.class);
				if (modelService != null) {
					IEclipseContext context = modelService.getContainingContext(selected);
					if (context != null) {
						ISaveHandler saveHandler = context.get(ISaveHandler.class);
						if (saveHandler != null) {
							if (saveHandler instanceof WWinPartServiceSaveHandler) {
								try {
									return ((WWinPartServiceSaveHandler) saveHandler).saveParts(parts, confirm, true, true);
								} catch (UnsupportedOperationException e) {
								}
							}
						}
					}
				}
			}
		}
		return saveAllEditors(confirm, closing);
	}

	private Collection<MPart> getDirtyMParts() {
		Set<MPart> dirtyParts = new HashSet<>();
		for (MWindow window : application.getChildren()) {
			IEclipseContext context = window.getContext();
			if (context != null) {
				IWorkbenchWindow wwindow = context.get(IWorkbenchWindow.class);
				if (wwindow != null) {
					EPartService partService = context.get(EPartService.class);
					if (partService != null) {
						Collection<MPart> parts = null;
						try {
							parts = partService.getDirtyParts();
							dirtyParts.addAll(parts);
						} catch (IllegalStateException e) {
						}
					}
				}
			}
		}
		return dirtyParts;
	}

