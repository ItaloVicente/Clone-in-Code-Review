	private boolean saveAllParts(boolean confirm, boolean closing) {

		EPartService partService = e4Context.get(EPartService.class);
		if (partService != null) {
			Collection<MPart> parts = partService.getDirtyParts();
			if (parts.size() > 0) {
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
							if (saveHandler instanceof PartServiceSaveHandler) {
								return ((PartServiceSaveHandler) saveHandler).saveParts(parts, confirm, true);
							}
							return saveHandler.saveParts(parts, confirm);
						}
					}
				}
			}
		}
		return saveAllEditors(confirm, closing);
	}

