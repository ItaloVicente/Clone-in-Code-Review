    private final Git git;
    private final String[] ids;

    public ResolveObjectIds(final Git git,
                            final String... ids) {
        this.git = git;
        this.ids = ids;
    }

    public List<ObjectId> execute() {
        final List<ObjectId> result = new ArrayList<>();

        for (final String id : ids) {
            try {
                final Ref refName = git.getRef(id);
                if (refName != null) {
                    result.add(refName.getObjectId());
                    continue;
                }

                try {
                    final ObjectId _id = ObjectId.fromString(id);
                    if (git.getRepository().getObjectDatabase().has(_id)) {
                        result.add(_id);
                    }
                } catch (final IllegalArgumentException ignored) {
                }
            } catch (final java.io.IOException ignored) {
            }
        }

        return result;
    }
