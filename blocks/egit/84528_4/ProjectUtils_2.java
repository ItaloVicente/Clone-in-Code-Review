					RepositoryFinder finder = new RepositoryFinder(project);
					finder.setFindInChildren(false);
					Collection<RepositoryMapping> mappings = finder
							.find(progress.newChild(1));
					if (!mappings.isEmpty()) {
						RepositoryMapping mapping = mappings.iterator().next();
						IPath absolutePath = mapping.getGitDirAbsolutePath();
						if (absolutePath != null) {
							projectsToConnect.put(project,
									absolutePath.toFile());
