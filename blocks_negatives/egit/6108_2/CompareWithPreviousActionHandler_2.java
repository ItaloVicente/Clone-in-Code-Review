		private PreviousCommit findPreviousCommit() {
			final AtomicReference<String> previousPath = new AtomicReference<String>();
			RevWalk rw = new RevWalk(repository);
			try {
				String path = getRepositoryPath();
				if (path.length() > 0) {
					FollowFilter filter = FollowFilter.create(path);
					filter.setRenameCallback(new RenameCallback() {

						public void renamed(DiffEntry entry) {
							if (previousPath.get() == null)
								previousPath.set(entry.getOldPath());
						}
					});
					rw.setTreeFilter(filter);
				}

				RevCommit headCommit = rw.parseCommit(repository.getRef(
						Constants.HEAD).getObjectId());
				rw.markStart(headCommit);
				headCommit = rw.next();

				if (headCommit == null)
					return null;
				RevCommit previousCommit = rw.next();
				if (previousCommit == null)
					return null;

				if (previousPath.get() == null)
					previousPath.set(getRepositoryPath());
				return new PreviousCommit(previousCommit, previousPath.get());
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			} finally {
				rw.dispose();
			}
			return null;
		}

