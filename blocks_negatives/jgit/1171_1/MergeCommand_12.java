			RevCommit headCommit = revWalk.lookupCommit(head.getObjectId());

			Ref ref = commits.get(0);

			refLogMessage.append(ref.getName());

			ObjectId objectId = ref.getPeeledObjectId();
			if (objectId == null)
				objectId = ref.getObjectId();

			RevCommit srcCommit = revWalk.lookupCommit(objectId);
			if (revWalk.isMergedInto(srcCommit, headCommit)) {
				setCallable(false);
				return new MergeResult(headCommit,
						MergeStatus.ALREADY_UP_TO_DATE, mergeStrategy);
			} else if (revWalk.isMergedInto(headCommit, srcCommit)) {
				refLogMessage.append(": " + MergeStatus.FAST_FORWARD);
				checkoutNewHead(revWalk, headCommit, srcCommit);
				updateHead(refLogMessage, srcCommit, head.getObjectId());
				setCallable(false);
				return new MergeResult(srcCommit, MergeStatus.FAST_FORWARD,
						mergeStrategy);
			} else {
				return new MergeResult(
						headCommit,
						MergeResult.MergeStatus.NOT_SUPPORTED,
						mergeStrategy,
						JGitText.get().onlyAlreadyUpToDateAndFastForwardMergesAreAvailable);
