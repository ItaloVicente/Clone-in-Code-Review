				if (fastForwardMode != null)
					merge.setFastForward(fastForwardMode);
				if (commit != null)
					merge.setCommit(commit.booleanValue());
				if (squash != null)
					merge.setSquash(squash.booleanValue());
