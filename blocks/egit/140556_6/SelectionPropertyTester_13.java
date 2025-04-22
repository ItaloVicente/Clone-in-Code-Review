				IResource[] resources = SelectionUtils
						.getSelectedResources(selection);
				Collection<Repository> repositories = getRepositories(
						resources);
				if (repositories.isEmpty()) {
					return false;
				}
				if (args != null && args.length > 0) {
					for (Repository repository : repositories) {
						if (!testRepositoryProperties(repository, args)) {
							return false;
						}
