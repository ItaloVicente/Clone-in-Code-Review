	private void updateHeadRef() throws TeamException {
		boolean detach = false;
		if (refName == null || !refName.startsWith(Constants.R_HEADS))
			detach = true;
		try {
			RefUpdate u = repository.updateRef(Constants.HEAD, detach);
			Result res;
			if (detach) {
				u.setNewObjectId(newCommit.getId());
				u.setRefLogMessage(NLS.bind(
						CoreText.BranchOperation_checkoutMovingTo, newCommit
								.getId().name()), false);
				res = u.forceUpdate();
			} else {
				u.setRefLogMessage(NLS.bind(
						CoreText.BranchOperation_checkoutMovingTo, refName),
						false);
				res = u.link(refName);
			}
			switch (res) {
			case NEW:
			case FORCED:
			case NO_CHANGE:
			case FAST_FORWARD:
				break;
			default:
				throw new IOException(u.getResult().name());
			}
		} catch (IOException e) {
			throw new TeamException(NLS.bind(
					CoreText.BranchOperation_updatingHeadToRef, refName), e);
		}
	}

	private void checkoutTree() throws TeamException {
		try {
			DirCacheCheckout dirCacheCheckout = new DirCacheCheckout(
					repository, oldTree, repository.lockDirCache(), newTree);
			dirCacheCheckout.setFailOnConflict(true);
			boolean result = dirCacheCheckout.checkout();
			if (!result)
				retryDelete(dirCacheCheckout);
		} catch (CheckoutConflictException e) {
			TeamException teamException = new TeamException(e.getMessage());
			throw teamException;
		} catch (IOException e) {
			throw new TeamException(CoreText.BranchOperation_checkoutProblem, e);
		}
	}

	private void retryDelete(DirCacheCheckout dirCacheCheckout) throws IOException {
		List<String> files = dirCacheCheckout.getToBeDeleted();
		for(String path:files) {
			File file = new File(repository.getWorkTree(), path);
			FileUtils.delete(file, FileUtils.RECURSIVE | FileUtils.RETRY);
		}
