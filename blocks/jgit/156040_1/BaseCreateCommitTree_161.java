	void addToTemporaryInCoreIndex(final DirCacheEditor editor
			final FileMode fileMode) {
		editor.add(new DirCacheEditor.PathEdit(dcEntry) {
			@Override
			public void apply(final DirCacheEntry ent) {
				ent.setObjectId(objectId);
				ent.setFileMode(fileMode);
			}
		});
	}
