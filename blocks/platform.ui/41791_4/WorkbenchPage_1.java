		return result.toArray(new ISaveablePart[result.size()]);
	}

	public IWorkbenchPart[] getDirtyWorkbenchParts() {
		List<IWorkbenchPart> result = new ArrayList<IWorkbenchPart>(3);
		IWorkbenchPartReference[] allParts = getSortedParts(true, true, true);
		for (int i = 0; i < allParts.length; i++) {
			IWorkbenchPartReference reference = allParts[i];
