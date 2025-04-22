    public CreateRevertCommitTree(final Git git,
                                  final ObjectId headId,
                                  final ObjectInserter inserter,
                                  final RevertCommitContent commitContent) {
        super(git,
              headId,
              inserter,
              commitContent);
    }

    public Optional<ObjectId> execute() {
        final DirCacheEditor editor = DirCache.newInCore().editor();

        try {
            iterateOverTreeWalk(git,
                                headId,
                                (walkPath, hTree) -> {
                                    addToTemporaryInCoreIndex(editor,
                                                              new DirCacheEntry(walkPath),
                                                              hTree.getEntryObjectId(),
                                                              hTree.getEntryFileMode());
                                });

            editor.finish();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        return buildTree(editor);
    }
