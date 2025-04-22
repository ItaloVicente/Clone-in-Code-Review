		Map<String, String> paramValues = new HashMap<>();
		for (IProject project : projects) {
			final boolean notAlreadyShared = RepositoryProvider
					.getProvider(project) == null;
			if (project.isAccessible() && notAlreadyShared)
				paramValues.put(project.getName(), project.getName());
		}
		return paramValues;
