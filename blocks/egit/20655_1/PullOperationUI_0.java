		Map<Repository, Object> res = new HashMap<Repository, Object>(
				this.results);
		for (Repository repo : res.keySet()) {
			Object result = res.get(repo);
			if (result instanceof PullResult) {
				PullResult pullResult = (PullResult) result;
				if (pullResult.getRebaseResult() != null
						&& RebaseResult.Status.UNCOMMITTED_CHANGES == pullResult
						.getRebaseResult().getStatus()) {
					handleUncommittedChanges(repo, pullResult
							.getRebaseResult().getUncommittedChanges());
					this.results.remove(repo);
				}
			}
		}
		if (this.results.isEmpty())
			return;
