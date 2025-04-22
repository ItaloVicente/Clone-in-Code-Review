					Ref ref = repository.findRef(sourceRef);
					if (ref != null)
						BranchOperationUI.createWithRef(repository,
								ref.getName()).start();
					else
						BranchOperationUI.create(repository).start();
