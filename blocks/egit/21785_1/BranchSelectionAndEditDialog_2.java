				String base = refNameFromDialog();
				if (base == null) {
					String sourceRef = repo.getConfig().getString(
							ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
							ConfigConstants.CONFIG_KEY_DEFBRANCHSTARTPOINT);
					try {
						if (repo.getRef(sourceRef) != null) {
							base = sourceRef;
						}
					} catch (IOException e1) {
					}
				}
				CreateBranchWizard wiz = new CreateBranchWizard(repo, base);
