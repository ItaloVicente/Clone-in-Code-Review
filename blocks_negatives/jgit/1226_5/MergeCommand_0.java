				RevCommit srcCommit = revWalk.lookupCommit(objectId);
				if (revWalk.isMergedInto(srcCommit, headCommit)) {
					setCallable(false);
					return new MergeResult(headCommit, srcCommit,
							new ObjectId[] { srcCommit, headCommit },
							MergeStatus.ALREADY_UP_TO_DATE, mergeStrategy);
				} else if (revWalk.isMergedInto(headCommit, srcCommit)) {
					refLogMessage.append(": " + MergeStatus.FAST_FORWARD);
					checkoutNewHead(revWalk, headCommit, srcCommit);
					updateHead(refLogMessage, srcCommit, head.getObjectId());
					setCallable(false);
					return new MergeResult(srcCommit, headCommit,
							new ObjectId[] { srcCommit, headCommit },
							MergeStatus.FAST_FORWARD, mergeStrategy);
