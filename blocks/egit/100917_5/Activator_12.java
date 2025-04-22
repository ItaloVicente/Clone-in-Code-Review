		private final IndexChangedListener listener = event -> {
			Repository repository = event.getRepository();
			if (repository.isBare()) {
				return;
			}
			List<String> directories = new ArrayList<>();
			for (IProject project : RuleUtil.getProjects(repository)) {
				if (project.isAccessible()) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(project);
					if (mapping != null
							&& repository == mapping.getRepository()) {
						String repoRelativePath = mapping
								.getRepoRelativePath(project);
						if (repoRelativePath == null) {
							continue;
						}
						if (GitTraceLocation.REPOSITORYCHANGESCANNER
								.isActive()) {
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.REPOSITORYCHANGESCANNER
											.getLocation(),
									"Scanning project " + project.getName()); //$NON-NLS-1$
						}
						try (TreeWalk w = new TreeWalk(repository)) {
							w.addTree(new FileTreeIterator(repository));
							if (!repoRelativePath.isEmpty()) {
								w.setFilter(PathFilterGroup
										.createFromStrings(repoRelativePath));
							} else {
								directories.add("/"); //$NON-NLS-1$
							}
							w.setRecursive(false);
							while (w.next()) {
								if (w.isSubtree()) {
									FileTreeIterator iter = w.getTree(0,
											FileTreeIterator.class);
									if (iter != null
											&& !iter.isEntryIgnored()) {
										directories
												.add(w.getPathString() + '/');
										w.enterSubtree();
									}
								}
							}
						} catch (IOException e) {
						}
						if (GitTraceLocation.REPOSITORYCHANGESCANNER
								.isActive()) {
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.REPOSITORYCHANGESCANNER
											.getLocation(),
									"Scanned project " + project.getName()); //$NON-NLS-1$
						}
					}
				}
			}
			if (directories.isEmpty()) {
				return;
			}
			repository
					.fireEvent(new WorkingTreeModifiedEvent(directories, null));
		};

