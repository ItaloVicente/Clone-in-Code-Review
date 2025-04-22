        final ObjectId tree = git.getTreeFromRef(branchName);
        if (tree == null) {
            return new PathInfo(null,
                                gitPath,
                                PathType.NOT_FOUND);
        }
        try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
            tw.setFilter(PathFilter.create(gitPath));
            tw.reset(tree);
            while (tw.next()) {
                if (tw.getPathString().equals(gitPath)) {
                    if (tw.getFileMode(0).equals(FileMode.TYPE_TREE)) {
                        return new PathInfo(tw.getObjectId(0),
                                            gitPath,
                                            PathType.DIRECTORY);
                    } else if (tw.getFileMode(0).equals(FileMode.TYPE_FILE) ||
                            tw.getFileMode(0).equals(FileMode.EXECUTABLE_FILE) ||
                            tw.getFileMode(0).equals(FileMode.REGULAR_FILE)) {
                        final long size = tw.getObjectReader().getObjectSize(tw.getObjectId(0),
                                                                             OBJ_BLOB);
                        return new PathInfo(tw.getObjectId(0),
                                            gitPath,
                                            PathType.FILE,
                                            size);
                    }
                }
                if (tw.isSubtree()) {
                    tw.enterSubtree();
                }
            }
        }
        return new PathInfo(null,
                            gitPath,
                            PathType.NOT_FOUND);
    }
