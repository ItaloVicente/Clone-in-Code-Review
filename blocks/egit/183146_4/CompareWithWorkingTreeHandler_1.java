		}
		IRepositoryCommit commit = Adapters.adapt(selection.getFirstElement(),
				IRepositoryCommit.class);
		if (commit != null) {
			return !commit.getRepository().isBare();
		}
		return false;
