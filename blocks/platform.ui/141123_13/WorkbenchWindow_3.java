		if (!getWorkbenchImpl().isClosing()) {
			return SaveAllParts(true, true, true);
		}
		return true;
	}

	private boolean SaveAllParts(boolean confirm, boolean closing, boolean addNonPartSources) {
		if (modelService != null) {
			Set<MPart> dirtyParts = new HashSet<>();
			final IEclipseContext context = model.getContext();
			if (context != null) {
				EPartService partService = context.get(EPartService.class);
				if (partService != null) {
					Collection<MPart> parts = null;
					try {
						parts = partService.getDirtyParts();
						dirtyParts.addAll(parts);
					} catch (IllegalStateException e) {
					}
				}
				if (dirtyParts != null && dirtyParts.size() > 0) {
					ISaveHandler saveHandler = context.get(ISaveHandler.class);
					if (saveHandler != null) {
						if (saveHandler instanceof WWinPartServiceSaveHandler) {
							try {
								return ((WWinPartServiceSaveHandler) saveHandler).saveParts(dirtyParts, confirm,
										closing, addNonPartSources);
							} catch (UnsupportedOperationException e) {
							}
						}
					}
				}
			}
		}
