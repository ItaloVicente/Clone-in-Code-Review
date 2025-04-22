			MergeResult mergeRes;
			try {
				mergeRes = merge.call();
				monitor.update(1);
				result = new PullResult(fetchRes, remote, mergeRes);
			} catch (NoHeadException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (ConcurrentRefUpdateException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (CheckoutConflictException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (InvalidMergeHeadsException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (WrongRepositoryStateException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (NoMessageException e) {
				throw new JGitInternalException(e.getMessage(), e);
			}
