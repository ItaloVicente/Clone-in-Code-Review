			RevCommit srcCommit = revWalk.lookupCommit(objectId);
			if (revWalk.isMergedInto(srcCommit
				setCallable(false);
				return new MergeResult(headCommit
						headCommit
						MergeStatus.ALREADY_UP_TO_DATE
			} else if (revWalk.isMergedInto(headCommit
				refLogMessage.append(": " + MergeStatus.FAST_FORWARD);
				checkoutNewHead(revWalk
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
				boolean noConflicts;
				Map<String
				if (merger instanceof ResolveMerger) {
					((ResolveMerger) merger).setCommitNames(new String[] {
							"BASE"
					noConflicts = merger.merge(headCommit
					lowLevelResults = ((ResolveMerger) merger)
							.getMergeResults();
				} else
					noConflicts = merger.merge(headCommit
