		return (ISaveablePart[]) result.toArray(new ISaveablePart[result.size()]);
	}

	public boolean saveAllEditors(boolean confirm, boolean closing, boolean addNonPartSources) {
		ISaveablePart[] parts = getDirtyParts();
		if (parts.length == 0) {
			return true;
		}
		List dirtyParts = new ArrayList(parts.length);
		for (int i = 0; i < parts.length; i++) {
			dirtyParts.add(parts[i]);
		}

		return saveAll(dirtyParts, confirm, closing, addNonPartSources, legacyWindow, legacyWindow);
	}

	public static boolean saveAll(List dirtyParts, final boolean confirm, final boolean closing,
			boolean addNonPartSources, final IRunnableContext runnableContext,
			final IShellProvider shellProvider) {
		dirtyParts = new ArrayList(dirtyParts);

		if (closing) {
			for (Iterator it = dirtyParts.iterator(); it.hasNext();) {
				ISaveablePart saveablePart = (ISaveablePart) it.next();
				if (!saveablePart.isSaveOnCloseNeeded()) {
					it.remove();
