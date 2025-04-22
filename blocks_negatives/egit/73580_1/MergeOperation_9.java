				} catch (IOException e) {
					throw new TeamException(CoreText.MergeOperation_InternalError, e);
				}
				if (fastForwardMode != null)
					merge.setFastForward(fastForwardMode);
				if (commit != null)
					merge.setCommit(commit.booleanValue());
				if (squash != null)
					merge.setSquash(squash.booleanValue());
				if (mergeStrategy != null) {
					merge.setStrategy(mergeStrategy);
				}
				if (message != null)
					merge.setMessage(message);
				try {
