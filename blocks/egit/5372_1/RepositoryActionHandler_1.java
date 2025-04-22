		Repository result = null;
		if (mapping == null)
			for (Object o : selection.toArray()) {
				Repository nextRepo = null;
				if (o instanceof Repository)
					nextRepo = (Repository) o;
				else if (o instanceof PlatformObject)
					nextRepo = (Repository) ((PlatformObject) o)
							.getAdapter(Repository.class);
				if (nextRepo != null && result != null
						&& !result.equals(nextRepo)) {
					if (warn)
						MessageDialog
								.openError(
										shell,
										UIText.RepositoryAction_multiRepoSelectionTitle,
										UIText.RepositoryAction_multiRepoSelection);
					return null;
				}
				result = nextRepo;
			}
		else
			result = mapping.getRepository();
		if (result == null) {
