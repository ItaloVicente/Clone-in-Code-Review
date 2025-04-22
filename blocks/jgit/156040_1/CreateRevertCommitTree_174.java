	public CreateRevertCommitTree(final Git git
			final RevertCommitContent commitContent) {
		super(git
	}

	public Optional<ObjectId> execute() {
		final DirCacheEditor editor = DirCache.newInCore().editor();

		try {
			iterateOverTreeWalk(git
				addToTemporaryInCoreIndex(editor
						hTree.getEntryFileMode());
			});

			editor.finish();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return buildTree(editor);
	}
