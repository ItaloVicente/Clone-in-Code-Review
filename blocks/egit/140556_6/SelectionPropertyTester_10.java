			}
			case "projectsWithRepositories": //$NON-NLS-1$
			{
				Repository repository = getRepositoryOfProjects(collection,
						false);
				return repository != null;
