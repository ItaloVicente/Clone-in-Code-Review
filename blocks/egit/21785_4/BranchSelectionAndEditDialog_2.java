				String base = refNameFromDialog();
				if (base == null) {
					String sourceRef = repo.getConfig().getString(
							ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
							ConfigConstants.CONFIG_KEY_DEFBRANCHSTARTPOINT);
					try {
						Ref ref = repo.getRef(sourceRef);
						if (ref != null) {
							base = ref.getName();
						}
					} catch (IOException e1) {
					}
				}
				CreateBranchWizard wiz = new CreateBranchWizard(repo, base);
