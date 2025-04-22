			PushBranchWizard wizard = null;
			Ref ref = getBranchRef(repository);
			if (ref != null) {
				wizard = new PushBranchWizard(repository, ref);
			} else {
				ObjectId id = repository.resolve(repository.getFullBranch());
				wizard = new PushBranchWizard(repository, id);
