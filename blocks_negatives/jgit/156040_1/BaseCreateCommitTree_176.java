    void iterateOverTreeWalk(final Git git,
                             final ObjectId headId,
                             final BiConsumer<String, CanonicalTreeParser> consumer) {
        if (headId != null) {
            try (final TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
                final int hIdx = treeWalk.addTree(new RevWalk(git.getRepository()).parseTree(headId));
                treeWalk.setRecursive(true);
