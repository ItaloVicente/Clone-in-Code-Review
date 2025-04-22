	public CreateCopyCommitTree(final Git git
			final CopyCommitContent commitContent) {
		super(git
	}

	public Optional<ObjectId> execute() {
		final Map<String

		final DirCacheEditor editor = DirCache.newInCore().editor();

		try {
			iterateOverTreeWalk(git
				final String toPath = content.get(walkPath);
				addToTemporaryInCoreIndex(editor
						hTree.getEntryFileMode());
				if (toPath != null) {
					addToTemporaryInCoreIndex(editor
							hTree.getEntryFileMode());
				}
			});

			editor.finish();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return buildTree(editor);
	}
