			RevCommit srcCommit = revWalk.lookupCommit(objectId);
			if (revWalk.isMergedInto(srcCommit
				setCallable(false);
				return new MergeResult(headCommit
						headCommit
						MergeStatus.ALREADY_UP_TO_DATE
			} else if (revWalk.isMergedInto(headCommit
				refLogMessage.append(": " + MergeStatus.FAST_FORWARD);
				DirCacheCheckout dco = new DirCacheCheckout(repo
						headCommit.getTree()
						srcCommit.getTree());
				dco.setFailOnConflict(true);
				dco.checkout();

				updateHead(refLogMessage
				setCallable(false);
				return new MergeResult(srcCommit
						headCommit
						mergeStrategy
			} else {
				repo.writeMergeCommitMsg("merging " + ref.getName() + " into "
						+ head.getName());
				repo.writeMergeHeads(Arrays.asList(ref.getObjectId()));
				ThreeWayMerger merger = (ThreeWayMerger) mergeStrategy
						.newMerger(repo);
				boolean noProblems;
				Map<String
				Map<String
				if (merger instanceof ResolveMerger) {
					((ResolveMerger) merger).setCommitNames(new String[] {
							"BASE"
					noProblems = merger.merge(headCommit
					lowLevelResults = ((ResolveMerger) merger)
							.getMergeResults();
					failingPaths = ((ResolveMerger) merger).getFailingPathes();
				} else
					noProblems = merger.merge(headCommit

				if (noProblems) {
					DirCacheCheckout dco = new DirCacheCheckout(repo
							headCommit.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.checkout();
					RevCommit newHead = new Git(getRepository()).commit().call();
					return new MergeResult(newHead.getId()
							null
									headCommit.getId()
							MergeStatus.MERGED
