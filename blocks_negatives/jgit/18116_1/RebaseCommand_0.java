			if (newHead != null) {
				String headName = rebaseState.readFile(HEAD_NAME);
				updateHead(headName, newHead, upstreamCommit);
				FileUtils.delete(rebaseState.getDir(), FileUtils.RECURSIVE);
				if (lastStepWasForward)
					return RebaseResult.FAST_FORWARD_RESULT;
				return RebaseResult.OK_RESULT;
			}
			return RebaseResult.FAST_FORWARD_RESULT;
