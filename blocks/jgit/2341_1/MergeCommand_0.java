
			ObjectId headId = head.getObjectId();
			if (headId == null) {
				revWalk.parseHeaders(srcCommit);
				DirCacheCheckout dco = new DirCacheCheckout(repo
						repo.lockDirCache()
				dco.setFailOnConflict(true);
				dco.checkout();
				RefUpdate refUpdate = repo
						.updateRef(head.getTarget().getName());
				refUpdate.setNewObjectId(objectId);
				refUpdate.setExpectedOldObjectId(null);
				refUpdate.setRefLogMessage("initial pull"
				if (refUpdate.update() != Result.NEW)
					throw new NoHeadException(
							JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
				setCallable(false);
				return new MergeResult(srcCommit
						null
						mergeStrategy
			}

			RevCommit headCommit = revWalk.lookupCommit(headId);

