		RevCommit upstream = walk.lookupCommit(upstreamCommit.getId());

		if (walk.isMergedInto(upstream
			return RebaseResult.UP_TO_DATE_RESULT;
		else if (walk.isMergedInto(headCommit
			monitor.beginTask(MessageFormat.format(
					JGitText.get().resettingHead
					upstreamCommit.getShortMessage())
			checkoutCommit(upstreamCommit);
			monitor.endTask();

			updateHead(headName
			return RebaseResult.FAST_FORWARD_RESULT;
		}

