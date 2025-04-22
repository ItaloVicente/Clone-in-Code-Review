	private final Git git;
	private final ObjectId startRange;
	private final ObjectId endRange;
	private final String path;

	public ListCommits(final Git git
		this.git = git;
		this.path = makeRelative(path);
		this.startRange = null;
		this.endRange = ref.getObjectId();
	}

	private static String makeRelative(String path) {
		return (path != null && path.startsWith("/")) ? path.substring(1) : path;
	}

	public ListCommits(final GitImpl git
		this.git = git;
		this.startRange = startRange;
		this.endRange = endRange;
		this.path = null;
	}

	public CommitHistory execute() throws IOException
		try (final RevWalk rw = buildWalk()) {
			if (path == null || path.isEmpty()) {
				return fullCommitHistory(rw);
			} else {
				return pathCommitHistory(rw);
			}
		}
	}

	private CommitHistory pathCommitHistory(final RevWalk rw)
			throws MissingObjectException
		final Map<AnyObjectId
		final List<RevCommit> commits = new ArrayList<>();
		final RenameCaptor renameCaptor = new RenameCaptor();
		final TreeRevFilter revFilter = createTreeRevFilter(rw
		String curPath = path;
		for (final RevCommit commit : rw) {
			if (revFilter.include(rw
				@SuppressWarnings("resource")
				final TreeWalk tw = new TreeWalk(rw.getObjectReader());
				tw.setRecursive(true);
				tw.setFilter(PathFilter.create(curPath));
				tw.addTree(commit.getTree());
				if (tw.next()) {
					commits.add(commit);
					pathByCommit.put(commit.getId()
					if (renameCaptor.hasCaptured()) {
						curPath = renameCaptor.getAndReset().getOldPath();
					}
				}
			}
		}

		return new CommitHistory(commits
	}

	private CommitHistory fullCommitHistory(final RevWalk rw) {
		final List<RevCommit> commits = stream(rw.spliterator()
		return new CommitHistory(commits
	}

	private TreeRevFilter createTreeRevFilter(final RevWalk rw
		final FollowFilter followFilter = FollowFilter.create(curPath
				git.getRepository().getConfig().get(DiffConfig.KEY));
		followFilter.setRenameCallback(renameCallback);
		final TreeRevFilter revFilter = new TreeRevFilter(rw
		return revFilter;
	}

	private RevWalk buildWalk() throws GitAPIException
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
