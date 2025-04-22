		StringBuilder calculatedCommitMessage = new StringBuilder();

		Set<IResource> resources = new HashSet<>();
		for (String path : paths) {
			IFile file = findFile(path);
			if (file != null)
				resources.add(file.getProject());
		}
		if (resources.size() == 0 && repository != null) {
			resources
					.addAll(Arrays.asList(ProjectUtil.getProjects(repository)));
		}
		List<ICommitMessageProvider> messageProviders = getCommitMessageProviders();
		IResource[] resourcesArray = resources.toArray(new IResource[0]);
