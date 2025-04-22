    private final Git git;
    private final ObjectId oldRef;
    private final ObjectId newRef;

    public ListDiffs(final Git git,
                     final ObjectId oldRef,
                     final ObjectId newRef) {
        this.git = git;
        this.oldRef = oldRef;
        this.newRef = newRef;
    }

    public List<DiffEntry> execute() {
        if (newRef == null || git.getRepository() == null) {
            return emptyList();
        }

        try (final ObjectReader reader = git.getRepository().newObjectReader()) {
            CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
            if (oldRef != null) {
                oldTreeIter.reset(reader,
                                  oldRef);
            }
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            newTreeIter.reset(reader,
                              newRef);
            return new CustomDiffCommand(git).setNewTree(newTreeIter).setOldTree(oldTreeIter).setShowNameAndStatusOnly(true).call();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
