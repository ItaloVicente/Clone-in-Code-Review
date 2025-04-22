
		if (pushTo == PushMode.GERRIT) {
			final Wizard pushWizard = new PushToGerritWizard(repository);
			openPushWizard(pushWizard);
		} else if (config == null) {
			try {
				Wizard pushWizard = null;
				String fullBranch = repository.getFullBranch();
				if (fullBranch != null
						&& fullBranch.startsWith(Constants.R_HEADS)) {
					Ref ref = repository.getRef(fullBranch);
					pushWizard = new PushBranchWizard(repository, ref);
				} else {
					pushWizard = new PushBranchWizard(repository,
							commit.getId());
