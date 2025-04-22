		if (pullRebaseMode != BranchRebaseMode.NONE) {
			try {
				Ref head = repo.exactRef(Constants.HEAD);
				if (head == null) {
					throw new NoHeadException(JGitText
							.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
				}
				ObjectId headId = head.getObjectId();
				if (headId == null) {
					try (RevWalk revWalk = new RevWalk(repo)) {
						RevCommit srcCommit = revWalk
								.parseCommit(commitToMerge);
						DirCacheCheckout dco = new DirCacheCheckout(repo,
								repo.lockDirCache(), srcCommit.getTree());
						dco.setFailOnConflict(true);
						dco.setProgressMonitor(monitor);
						dco.checkout();
						RefUpdate refUpdate = repo
								.updateRef(head.getTarget().getName());
						refUpdate.setNewObjectId(commitToMerge);
						refUpdate.setExpectedOldObjectId(null);
						refUpdate.setRefLogMessage("initial pull", false); //$NON-NLS-1$
						if (refUpdate.update() != Result.NEW) {
							throw new NoHeadException(JGitText
									.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
