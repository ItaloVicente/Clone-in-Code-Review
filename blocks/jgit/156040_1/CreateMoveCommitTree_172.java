	public CreateMoveCommitTree(final Git git
			final MoveCommitContent commitContent) {
		super(git
	}

	public Optional<ObjectId> execute() {
		final Map<String
		final DirCacheEditor editor = DirCache.newInCore().editor();
		final List<String> pathsAdded = new ArrayList<>();

		try {
			iterateOverTreeWalk(git
				final String toPath = content.get(walkPath);
				final DirCacheEntry dcEntry = new DirCacheEntry((toPath == null) ? walkPath : toPath);
				if (!pathsAdded.contains(dcEntry.getPathString())) {
					addToTemporaryInCoreIndex(editor
					pathsAdded.add(dcEntry.getPathString());
				}
			});
			editor.finish();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return buildTree(editor);
	}
