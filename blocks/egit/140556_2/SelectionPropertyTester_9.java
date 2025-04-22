		if (null != property)
			switch (property) {
			case "projectsSingleRepository": //$NON-NLS-1$
			{
				Repository repository = getRepositoryOfProjects(collection,
						true);
				return testRepositoryProperties(repository, args);
