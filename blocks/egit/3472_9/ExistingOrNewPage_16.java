			if (!internalMode) {
				File workdir = selectedRepository.getWorkTree();
				IProject project = (IProject) ti;
				IPath targetLocation = new Path(relPath.getText())
						.append(project.getName());
				File targetFile = new File(workdir, targetLocation.toOSString());
				ret.put(project, targetFile);

			} else {
				final IProject project = ((ProjectAndRepo) ti).getProject();
				String path = ((ProjectAndRepo) ti).getRepo();
				final IPath selectedRepo = Path.fromOSString(path);
				IPath localPathToRepo = selectedRepo;
				if (!selectedRepo.isAbsolute()) {
					localPathToRepo = project.getLocation()
							.append(selectedRepo);
				}
				ret.put(project, localPathToRepo.toFile());
