					Ref ref = null;
					if (sourceRef != null) {
						ref = repository.findRef(sourceRef);
					}
					if (ref != null) {
						wiz = new CreateBranchWizard(repository, ref.getName());
					} else {
						wiz = new CreateBranchWizard(repository,
								repository.getFullBranch());
					}
