    public void execute() {
        try {
            git.refUpdate(target,
                          git.resolveRevCommit(git.resolveObjectIds(source).get(0)));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
