			RevCommit commit = AdapterUtils.adapt(receiver, RevCommit.class);
			if (commit == null) {
				return false;
			}
			if (expectedValue instanceof Integer) {
				return commit.getParentCount() <= ((Integer) expectedValue)
						.intValue();
			} else {
				return computeResult(expectedValue,
						commit.getParentCount() > 0);
			}
			Repository repository = AdapterUtils.adapt(receiver,
					Repository.class);
			if (repository != null) {
				return computeResult(expectedValue, repository.isBare());
			}
			Repository repository = AdapterUtils.adapt(receiver,
					Repository.class);
			if (repository != null) {
				return computeResult(expectedValue, repository
						.getRepositoryState().equals(RepositoryState.SAFE));
			}
			Repository repository = AdapterUtils.adapt(receiver,
					Repository.class);
			if (repository != null) {
				return computeResult(expectedValue,
						repository.getRepositoryState().canCommit());
			}
			IRepositoryCommit commit = AdapterUtils.adapt(receiver,
					IRepositoryCommit.class);
			if (commit != null) {
				return computeResult(expectedValue,
						hasMultipleRefs(commit, toRefNames(args)));
			}
			IRepositoryCommit commit = AdapterUtils.adapt(receiver,
					IRepositoryCommit.class);
			if (commit != null) {
				return computeResult(expectedValue,
						hasRef(commit, toRefNames(args)));
			}
			RepositoryCommit commit = AdapterUtils.adapt(receiver,
					RepositoryCommit.class);
