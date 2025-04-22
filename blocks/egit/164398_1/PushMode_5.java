	UPSTREAM {
		@Override
		public Wizard getWizard(@NonNull Repository repository,
				RevCommit commit)
				throws IOException {
			String fullBranch = repository.getFullBranch();
			if (fullBranch != null
					&& fullBranch.startsWith(Constants.R_HEADS)) {
				Ref ref = repository.exactRef(fullBranch);
				return new PushBranchWizard(repository, ref);
			} else if (commit != null) {
				return new PushBranchWizard(repository, commit.getId());
			} else {
				ObjectId head = repository.resolve(Constants.HEAD);
				if (head != null) {
					return new PushBranchWizard(repository, head);
				}
			}
			return null;
		}
	},

