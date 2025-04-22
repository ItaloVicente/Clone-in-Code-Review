	public IWorkbenchPart[] getDirtyWorkbenchParts() {
		List<IWorkbenchPart> result = new ArrayList<IWorkbenchPart>(3);
		IWorkbenchPartReference[] allParts = getSortedParts(true, true, true);
		for (int i = 0; i < allParts.length; i++) {
			IWorkbenchPartReference reference = allParts[i];

			IWorkbenchPart part = reference.getPart(false);
			ISaveablePart saveable = SaveableHelper.getSaveable(part);
			if (saveable != null) {
				if (saveable.isDirty()) {
					result.add(part);
				}
			}
		}
		return result.toArray(new IWorkbenchPart[result.size()]);
