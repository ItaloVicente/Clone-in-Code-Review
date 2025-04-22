	public boolean saveAllEditors(boolean confirm, boolean closing, boolean addNonPartSources) {
		ISaveablePart[] parts = getDirtyParts();
		if (parts.length == 0) {
			return true;
		}
		List dirtyParts = new ArrayList(parts.length);
		for (int i = 0; i < parts.length; i++) {
			dirtyParts.add(parts[i]);
		}
