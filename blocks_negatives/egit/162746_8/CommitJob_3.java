				String fullBranch = repository.getFullBranch();
				if (fullBranch != null
						&& fullBranch.startsWith(Constants.R_HEADS)) {
					Ref ref = repository.exactRef(fullBranch);
					return new PushBranchWizard(repository, ref);
				} else {
					return new PushBranchWizard(repository,
							commit.getId());
				}
