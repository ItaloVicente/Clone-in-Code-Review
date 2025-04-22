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
						DirCacheCheckout dco = new DirCacheCheckout(repo
								repo.lockDirCache()
						dco.setFailOnConflict(true);
						dco.setProgressMonitor(monitor);
						dco.checkout();
						RefUpdate refUpdate = repo
								.updateRef(head.getTarget().getName());
						refUpdate.setNewObjectId(commitToMerge);
						refUpdate.setExpectedOldObjectId(null);
						refUpdate.setRefLogMessage("initial pull"
						if (refUpdate.update() != Result.NEW) {
							throw new NoHeadException(JGitText
									.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
						}
						monitor.endTask();
						return new PullResult(fetchRes
								RebaseResult.result(
										RebaseResult.Status.FAST_FORWARD
										srcCommit));
					}
				}
			} catch (NoHeadException e) {
				throw e;
			} catch (IOException e) {
				throw new JGitInternalException(JGitText
						.get().exceptionCaughtDuringExecutionOfPullCommand
			}
