				projects.add(file.getProject());
		}
		Set<IResource> preselectedResources = new HashSet<IResource>();
		for (String preselectedPath : preselectedPaths) {
			IFile file = findFile(preselectedPath);
			if (file != null)
				preselectedResources.add(file);
