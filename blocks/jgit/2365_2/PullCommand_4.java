				mergeRes = merge.call();
				monitor.update(1);
				result = new PullResult(fetchRes
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
