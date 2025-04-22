    public CreateMoveCommitTree(final Git git,
                                final ObjectId headId,
                                final ObjectInserter inserter,
                                final MoveCommitContent commitContent) {
        super(git,
              headId,
              inserter,
              commitContent);
    }

    public Optional<ObjectId> execute() {
        final Map<String, String> content = commitContent.getContent();
        final DirCacheEditor editor = DirCache.newInCore().editor();
        final List<String> pathsAdded = new ArrayList<>();

        try {
            iterateOverTreeWalk(git,
                                headId,
                                (walkPath, hTree) -> {
                                    final String toPath = content.get(walkPath);
                                    final DirCacheEntry dcEntry = new DirCacheEntry((toPath == null) ? walkPath : toPath);
                                    if (!pathsAdded.contains(dcEntry.getPathString())) {
                                        addToTemporaryInCoreIndex(editor,
                                                                  dcEntry,
                                                                  hTree.getEntryObjectId(),
                                                                  hTree.getEntryFileMode());
                                        pathsAdded.add(dcEntry.getPathString());
                                    }
                                });
            editor.finish();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        return buildTree(editor);
    }
