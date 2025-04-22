	private RebaseResult finishRebase(RevCommit newHead
			boolean lastStepWasForward) throws IOException {
		String headName = rebaseState.readFile(HEAD_NAME);
		updateHead(headName
		FileUtils.delete(rebaseState.getDir()
		if (lastStepWasForward || newHead == null)
			return RebaseResult.FAST_FORWARD_RESULT;
		return RebaseResult.OK_RESULT;
	}

