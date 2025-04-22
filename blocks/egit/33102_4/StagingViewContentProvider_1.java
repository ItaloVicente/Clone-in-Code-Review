	private void setSymlinkFileMode(Collection<StagingEntry> entries) {
		final FS fs = repository.getFS();

		if (!fs.supportsSymlinks()) {
			for (StagingEntry entry : entries)
				entry.setSymlink(false);
			return;
		}

		final RevWalk revWalk = new RevWalk(repository);

		try {
			final ObjectId headCommitId = repository.resolve(Constants.HEAD);
			final RevCommit headCommit = revWalk.parseCommit(headCommitId);
			final RevTree headTree = headCommit.getTree();

			for (StagingEntry entry : entries)
				entry.setSymlink(isSymlink(entry, fs, headTree));

		} catch (Exception e) {
			Activator.error(UIText.StagingViewContentProvider_SymlinkError, e);
		} finally {
			revWalk.release();
		}
	}

	private boolean isSymlink(StagingEntry entry, FS fs, RevTree headTree) {
		boolean symlink = false;
		State state = entry.getState();
		try {
			if (State.REMOVED == state || State.MISSING == state
					|| State.MISSING_AND_CHANGED == state) {
				if (headTree != null) {
					TreeWalk tw = TreeWalk.forPath(repository, entry.getPath(),
							headTree);
					if (FileMode.SYMLINK == tw.getFileMode(0))
						symlink = true;
				}
			} else {
				if (fs.isSymLink(entry.getLocation().toFile()))
					symlink = true;
			}
		} catch (Exception e) {
			Activator.error(UIText.StagingViewContentProvider_SymlinkError, e);
		}

		return symlink;
	}
