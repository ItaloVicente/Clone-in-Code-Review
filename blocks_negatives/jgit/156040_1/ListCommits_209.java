    private final Git git;
    private final ObjectId startRange;
    private final ObjectId endRange;
    private final String path;

    public ListCommits(final Git git,
                       final Ref ref,
                       final String path) {
        this.git = git;
        this.path = makeRelative(path);
        this.startRange = null;
        this.endRange = ref.getObjectId();
    }

    private static String makeRelative(String path) {
        return (path != null && path.startsWith("/")) ? path.substring(1) : path;
    }

    public ListCommits(final GitImpl git,
                       final ObjectId startRange,
                       final ObjectId endRange) {
        this.git = git;
        this.startRange = startRange;
        this.endRange = endRange;
        this.path = null;
    }

    public CommitHistory execute() throws IOException, GitAPIException {
        try (final RevWalk rw = buildWalk()) {
            if (path == null || path.isEmpty()) {
                return fullCommitHistory(rw);
            } else {
                return pathCommitHistory(rw);
            }
        }
    }

    private CommitHistory pathCommitHistory(final RevWalk rw) throws MissingObjectException, IncorrectObjectTypeException, IOException {
        final Map<AnyObjectId, String> pathByCommit = new HashMap<>();
        final List<RevCommit> commits = new ArrayList<>();
        final RenameCaptor renameCaptor = new RenameCaptor();
        /*
         * We have to go through all commits and filter ourselves so that we can use the
         * rename callback to map commits to path renames.
         */
        final TreeRevFilter revFilter = createTreeRevFilter(rw, path, renameCaptor);
        String curPath = path;
        for (final RevCommit commit : rw) {
            if (revFilter.include(rw, commit)) {
                @SuppressWarnings("resource")
                final TreeWalk tw = new TreeWalk(rw.getObjectReader());
                tw.setRecursive(true);
                tw.setFilter(PathFilter.create(curPath));
                tw.addTree(commit.getTree());
                if (tw.next()) {
                    commits.add(commit);
                    pathByCommit.put(commit.getId(), curPath);
                    if (renameCaptor.hasCaptured()) {
                        curPath = renameCaptor.getAndReset().getOldPath();
                    }
                }
            }
        }

        return new CommitHistory(commits, pathByCommit, path);
    }

    private CommitHistory fullCommitHistory(final RevWalk rw) {
        final List<RevCommit> commits = stream(rw.spliterator(), false).collect(toList());
        return new CommitHistory(commits, Collections.emptyMap(), null);
    }

    private TreeRevFilter createTreeRevFilter(final RevWalk rw, String curPath, final RenameCallback renameCallback) {
        final FollowFilter followFilter = FollowFilter.create(curPath, git.getRepository().getConfig().get(DiffConfig.KEY));
        followFilter.setRenameCallback(renameCallback);
        final TreeRevFilter revFilter = new TreeRevFilter(rw, followFilter);
        return revFilter;
    }

    private RevWalk buildWalk() throws GitAPIException, IOException {
        final RevWalk rw = new RevWalk(git.getRepository());
        rw.setTreeFilter(TreeFilter.ANY_DIFF);
        rw.markStart(rw.parseCommit(endRange));
        rw.sort(RevSort.TOPO);
        if (startRange != null) {
            rw.markUninteresting(rw.parseCommit(startRange));
        }

        return rw;
    }

    private static class RenameCaptor extends RenameCallback {

        private DiffEntry captured;

        @Override
        public void renamed(final DiffEntry entry) {
            captured = entry;
        }

        public boolean hasCaptured() {
            return captured != null;
        }

        public DiffEntry getAndReset() {
            if (captured == null) {
                throw new NullPointerException("Cannot get DiffEntry when none was captured.");
            }

            final DiffEntry retVal = captured;
            captured = null;

            return retVal;
        }
    }
