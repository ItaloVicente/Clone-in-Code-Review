				String sourceRef = repository.getConfig().getString(
						ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
						ConfigConstants.CONFIG_KEY_DEFBRANCHSTARTPOINT);
				CreateBranchWizard wiz = null;
				try {
					Ref ref = null;
					if (sourceRef != null) {
						ref = repository.findRef(sourceRef);
