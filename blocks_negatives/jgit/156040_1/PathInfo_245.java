    public PathInfo(final ObjectId objectId,
                    final String path,
                    final FileMode fileMode) {
        this(objectId,
             path,
             convert(fileMode),
             -1);
    }
