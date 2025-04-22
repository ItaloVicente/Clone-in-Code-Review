	private boolean isSymlink(StagingEntry entry) {
		boolean symlink = false;
		try {
			FS fs = repository.getFS();
			if (!fs.supportsSymlinks()) {
				return false;
			}
			State state = entry.getState();
			if (State.REMOVED == state || State.MISSING == state
					|| State.MISSING_AND_CHANGED == state) {
				ObjectId headCommitId = repository.resolve(Constants.HEAD);
				if (headCommitId != null) {
					RevWalk revWalk = new RevWalk(repository);
					RevCommit headCommit = revWalk.parseCommit(headCommitId);
					RevTree headTree = headCommit.getTree();
					TreeWalk tw = TreeWalk.forPath(repository, entry.getPath(),
							headTree);
					if (FileMode.SYMLINK == tw.getFileMode(0))
						symlink = true;
				}
			} else {
				if (fs.isSymLink(entry.getLocation().toFile()))
					symlink = true;
			}
		} catch (IOException e) {
			Activator.error(UIText.StagingViewContentProvider_SymlinkError, e);
		}
		return symlink;
	}
