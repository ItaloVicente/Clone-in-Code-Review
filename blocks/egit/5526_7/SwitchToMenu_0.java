				String sourceRef = repository.getConfig().getString(
						ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
						ConfigConstants.CONFIG_KEY_DEFBRANCHSTARTPOINT);
				try {
					if (repository.getRef(sourceRef) != null)
						BranchOperationUI.createWithRef(repository, sourceRef).start();
					else
						BranchOperationUI.create(repository).start();
				} catch (IOException e1) {
					BranchOperationUI.create(repository).start();
				}
