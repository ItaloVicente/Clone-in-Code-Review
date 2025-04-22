
						RepositoryFinder finder = new RepositoryFinder(project);
						finder.setFindInChildren(false);
						Collection<RepositoryMapping> mappings = finder
								.find(new SubProgressMonitor(actMonitor, 1));
						if (!mappings.isEmpty()) {
							RepositoryMapping mapping = mappings.iterator()
									.next();
							projectsToConnect.put(project, mapping
									.getGitDirAbsolutePath().toFile());
