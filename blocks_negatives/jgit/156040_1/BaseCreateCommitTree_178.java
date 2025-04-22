    void addToTemporaryInCoreIndex(final DirCacheEditor editor,
                                   final DirCacheEntry dcEntry,
                                   final ObjectId objectId,
                                   final FileMode fileMode) {
        editor.add(new DirCacheEditor.PathEdit(dcEntry) {
            @Override
            public void apply(final DirCacheEntry ent) {
                ent.setObjectId(objectId);
                ent.setFileMode(fileMode);
            }
        });
    }
