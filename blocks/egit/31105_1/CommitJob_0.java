						PushBranchWizard pushWizard = null;
						if (repository.getBranch() != null) {
							Ref ref = repository.getRef(repository.getBranch());
							pushWizard = new PushBranchWizard(repository, ref);
						} else {
							pushWizard = new PushBranchWizard(repository,
									commit.getId());
						}
