	String findPreviousPath(final String path, final RevCommit commit) {
		final AtomicReference<String> previousPath = new AtomicReference<String>();
		RevWalk rw = new RevWalk(db);
		try {
			FollowFilter filter = FollowFilter.create(path);
			filter.setRenameCallback(new RenameCallback() {

				public void renamed(DiffEntry entry) {
					previousPath.set(entry.getOldPath());
				}
			});
			rw.setTreeFilter(filter);
			rw.markStart(commit);
			if (rw.next() != null)
				rw.next();
			return previousPath.get();
		} catch (IOException e) {
			return null;
		} finally {
			rw.dispose();
		}
	}

