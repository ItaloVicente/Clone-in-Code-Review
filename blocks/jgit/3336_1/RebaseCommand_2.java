			MergeCommand merge = new Git(repo).merge().include(upstreamCommit);
			try {
				MergeResult call = merge.call();
				switch (call.getMergeStatus()) {
				case FAST_FORWARD:
					return new RebaseResult(Status.FAST_FORWARD);
				case ALREADY_UP_TO_DATE:
					return new RebaseResult(Status.UP_TO_DATE);
				default:
					throw new JGitInternalException("Unexpected merge result: "
							+ call.getMergeStatus().toString());
				}
			 } catch (NoHeadException e) {
				throw new JGitInternalException(e.getMessage()
			 } catch (ConcurrentRefUpdateException e) {
				throw new JGitInternalException(e.getMessage()
			 } catch (CheckoutConflictException e) {
				throw new JGitInternalException(e.getMessage()
			 } catch (InvalidMergeHeadsException e) {
				throw new JGitInternalException(e.getMessage()
			 } catch (WrongRepositoryStateException e) {
				throw new JGitInternalException(e.getMessage()
			 } catch (NoMessageException e) {
				throw new JGitInternalException(e.getMessage()
			 }
