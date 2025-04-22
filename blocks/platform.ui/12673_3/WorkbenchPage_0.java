	public ISaveablePart[] getDirtyParts() {
		List result = new ArrayList(3);
		IWorkbenchPartReference[] allParts = getSortedParts();
		for (int i = 0; i < allParts.length; i++) {
			IWorkbenchPartReference reference = allParts[i];

			IWorkbenchPart part = reference.getPart(false);
			if (part != null && part instanceof ISaveablePart) {
				ISaveablePart saveable = (ISaveablePart) part;
				if (saveable.isDirty()) {
					result.add(saveable);
				}
			}
		}

		return (ISaveablePart[]) result.toArray(new ISaveablePart[result.size()]);
	}

	public boolean saveAllEditors(boolean confirm, boolean closing) {
