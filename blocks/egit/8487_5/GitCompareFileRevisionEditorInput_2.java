	FileRevisionTypedElement getAncestorRevision() {
		if (ancestor instanceof FileRevisionTypedElement)
			return (FileRevisionTypedElement) ancestor;
		return null;
	}

	private static void ensureContentsCached(FileRevisionTypedElement left, FileRevisionTypedElement right, FileRevisionTypedElement ancestor,
