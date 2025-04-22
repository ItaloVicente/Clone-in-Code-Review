			IResource resource) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping == null) {
			MessageDialog.openError(getShell(part),
					UIText.RepositoryAction_errorFindingRepoTitle,
					UIText.RepositoryAction_errorFindingRepo);
			return null;
		}

		return new PatchOperationUI(null, mapping.getRepository(), resource);
