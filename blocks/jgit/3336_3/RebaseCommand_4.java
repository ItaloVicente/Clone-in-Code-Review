
		if (headId.equals(upstreamCommit.getId()))
			return RebaseResult.UP_TO_DATE_RESULT;

		if (walk.isMergedInto(headCommit
			monitor.beginTask(MessageFormat.format(
					JGitText.get().resettingHead
					upstreamCommit.getShortMessage())
			checkoutCommit(upstreamCommit);
			monitor.endTask();

			updateHead(headName
			return new RebaseResult(RebaseResult.Status.FAST_FORWARD);
		}

