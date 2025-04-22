    public PathInfo(final ObjectId objectId,
                    final String path,
                    final FileMode fileMode,
                    final long size) {
        this(objectId,
             path,
             convert(fileMode));
    }
