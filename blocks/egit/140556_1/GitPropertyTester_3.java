		if (null != property) switch (property) {
			case "parentCount": //$NON-NLS-1$
			{
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
		}
			case "isBare": { //$NON-NLS-1$
		    Repository repository = AdapterUtils.adapt(receiver,
			    Repository.class);
		    if (repository != null) {
			return computeResult(expectedValue, repository.isBare());
		    }
			break;
		    }
			case "isSafe": { //$NON-NLS-1$
		    Repository repository = AdapterUtils.adapt(receiver,
			    Repository.class);
		    if (repository != null) {
			return computeResult(expectedValue, repository
				.getRepositoryState().equals(RepositoryState.SAFE));
		    }
			break;
		    }
			case "canCommit": { //$NON-NLS-1$
		    Repository repository = AdapterUtils.adapt(receiver,
			    Repository.class);
		    if (repository != null) {
			return computeResult(expectedValue,
				repository.getRepositoryState().canCommit());
		    }
			break;
		    }
			case "hasMultipleRefs": { //$NON-NLS-1$
		    IRepositoryCommit commit = AdapterUtils.adapt(receiver,
			    IRepositoryCommit.class);
		    if (commit != null) {
			return computeResult(expectedValue,
				hasMultipleRefs(commit, toRefNames(args)));
		    }
			break;
		    }
			case "hasRef": { //$NON-NLS-1$
		    IRepositoryCommit commit = AdapterUtils.adapt(receiver,
			    IRepositoryCommit.class);
		    if (commit != null) {
