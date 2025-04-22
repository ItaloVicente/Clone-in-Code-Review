			try {
				RevCommit headCommit = revWalk.lookupCommit(head.getObjectId());

				Ref ref = commits.get(0);

				refLogMessage.append(ref.getName());

				ObjectId objectId = ref.getPeeledObjectId();
				if (objectId == null)
					objectId = ref.getObjectId();

				RevCommit srcCommit = revWalk.lookupCommit(objectId);
				if (revWalk.isMergedInto(srcCommit
					setCallable(false);
					return new MergeResult(headCommit
							MergeStatus.ALREADY_UP_TO_DATE
				} else if (revWalk.isMergedInto(headCommit
					refLogMessage.append(": " + MergeStatus.FAST_FORWARD);
					checkoutNewHead(revWalk
					updateHead(refLogMessage
					setCallable(false);
					return new MergeResult(srcCommit
							mergeStrategy);
				} else {
					return new MergeResult(
							headCommit
							MergeResult.MergeStatus.NOT_SUPPORTED
							mergeStrategy
							JGitText.get().onlyAlreadyUpToDateAndFastForwardMergesAreAvailable);
				}
			} finally {
				revWalk.release();
