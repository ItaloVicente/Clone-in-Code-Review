			final IProject project = ((ProjectAndRepo)ti).getProject();
			String path = ((ProjectAndRepo)ti).getRepo();
			final IPath selectedRepo = Path.fromOSString(path);
			IPath localPathToRepo = selectedRepo;
			if (!selectedRepo.isAbsolute()) {
				localPathToRepo = project.getLocation().append(selectedRepo);
