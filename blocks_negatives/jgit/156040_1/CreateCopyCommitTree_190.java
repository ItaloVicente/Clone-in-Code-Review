    public CreateCopyCommitTree(final Git git,
                                final ObjectId headId,
                                final ObjectInserter inserter,
                                final CopyCommitContent commitContent) {
        super(git,
              headId,
              inserter,
              commitContent);
    }

    public Optional<ObjectId> execute() {
        final Map<String, String> content = commitContent.getContent();

        final DirCacheEditor editor = DirCache.newInCore().editor();

        try {
            iterateOverTreeWalk(git,
                                headId,
                                (walkPath, hTree) -> {
                                    final String toPath = content.get(walkPath);
                                    addToTemporaryInCoreIndex(editor,
                                                              new DirCacheEntry(walkPath),
                                                              hTree.getEntryObjectId(),
                                                              hTree.getEntryFileMode());
                                    if (toPath != null) {
                                        addToTemporaryInCoreIndex(editor,
                                                                  new DirCacheEntry(toPath),
                                                                  hTree.getEntryObjectId(),
                                                                  hTree.getEntryFileMode());
                                    }
                                });

            editor.finish();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        return buildTree(editor);
    }
