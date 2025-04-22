    public Ref execute() {
        try {
            final Ref value = repo.getRefDatabase().findRef(name);
            if (value != null) {
                return value;
            }
            final ObjectId treeRef = repo.resolve(name + "^{tree}");
            if (treeRef != null) {
                try (final ObjectReader objectReader = repo.getObjectDatabase().newReader()) {
                    final ObjectLoader loader = objectReader.open(treeRef);
                    if (loader.getType() == OBJ_TREE) {
                        return new ObjectIdRef.PeeledTag(Ref.Storage.NEW,
                                                         name,
                                                         ObjectId.fromString(name),
                                                         treeRef);
                    }
                }
            }
        } catch (final Exception ignored) {
        }
        return null;
    }
