			AnyObjectId commitToMerge;

			if (isRemote) {
				Ref r = null;
				if (fetchRes != null) {
					r = fetchRes.getAdvertisedRef(remoteBranchName);
					if (r == null)
						r = fetchRes.getAdvertisedRef(Constants.R_HEADS
								+ remoteBranchName);
				}
				if (r == null)
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().couldNotGetAdvertisedRef,
							remoteBranchName));
				else
					commitToMerge = r.getObjectId();
			} else {
				try {
					commitToMerge = repo.resolve(remoteBranchName);
				} catch (IOException e) {
					throw new JGitInternalException(
							JGitText.get().exceptionCaughtDuringExecutionOfPullCommand,
							e);
				}
			}
