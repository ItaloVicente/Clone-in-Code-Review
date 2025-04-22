				String sourceRef = repository.getConfig().getString(
						ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
						ConfigConstants.CONFIG_KEY_DEFBRANCHSTARTPOINT);
				try {
					Ref ref = repository.getRef(sourceRef);
					if (ref != null)
						BranchOperationUI.createWithRef(repository,
								ref.getName()).start();
					else
						BranchOperationUI.create(repository).start();
				} catch (IOException e1) {
					BranchOperationUI.create(repository).start();
				}
