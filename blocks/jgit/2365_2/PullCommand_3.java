					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().couldNotGetAdvertisedRef
							remoteBranchName));
				else
					commitToMerge = r.getObjectId();
			} else {
				try {
					commitToMerge = repo.resolve(remoteBranchName);
				} catch (IOException e) {
					throw new JGitInternalException(
							JGitText.get().exceptionCaughtDuringExecutionOfPullCommand
							e);
				}
