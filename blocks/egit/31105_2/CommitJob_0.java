						PushBranchWizard pushWizard = null;
						String fullBranch = repository.getFullBranch();
						if (fullBranch != null
								&& fullBranch.startsWith(Constants.R_HEADS)) {
							Ref ref = repository.getRef(repository.getBranch());
							pushWizard = new PushBranchWizard(repository, ref);
						} else {
							pushWizard = new PushBranchWizard(repository,
									commit.getId());
						}
