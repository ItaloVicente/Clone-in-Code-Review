	Optional<ObjectId> buildTree(final DirCacheEditor editor) {
		try {
			return Optional.ofNullable(editor.getDirCache().writeTree(odi));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
