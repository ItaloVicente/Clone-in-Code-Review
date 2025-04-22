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
				}
