		private void safeRefresh(IProject project, IProgressMonitor monitor)
				throws CoreException {
			if (!project.isAccessible() || monitor.isCanceled()) {
				return;
			}
			SubMonitor progress = SubMonitor.convert(monitor, 10);
			project.refreshLocal(IResource.DEPTH_ONE, progress.newChild(1));
			Map<String, IResource> toRefresh = new HashMap<>();
			for (IResource r : project.members()) {
				if (r.getType() == IResource.FOLDER) {
					toRefresh.put(r.getName(), r);
				}
			}
			if (toRefresh.isEmpty()) {
				return;
			}
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			Repository repo = null;
			if (mapping != null) {
				repo = mapping.getRepository();
			}
			if (mapping == null || repo == null) {
				project.refreshLocal(IResource.DEPTH_INFINITE,
						progress.newChild(9));
			} else {
				WorkingTreeIterator treeIterator = IteratorService
						.createInitialIterator(repo);
				if (treeIterator == null) {
					project.refreshLocal(IResource.DEPTH_INFINITE,
							progress.newChild(9));
					return;
				}
				String repoRelativePath = mapping.getRepoRelativePath(project);
				if (repoRelativePath == null) {
					project.refreshLocal(IResource.DEPTH_INFINITE,
							progress.newChild(9));
					return;
				}
				progress.setWorkRemaining(toRefresh.size());
				try (TreeWalk walk = new TreeWalk(repo)) {
					walk.addTree(treeIterator);
					TreeFilter filter = new NotIgnoredFilter(0);
					if (!repoRelativePath.isEmpty()) {
						filter = AndTreeFilter.create(PathFilterGroup
								.createFromStrings(repoRelativePath),
								filter);
					}
					walk.setFilter(filter);
					boolean inProject = repoRelativePath.isEmpty();
					while (walk.next()) {
						if (progress.isCanceled()) {
							return;
						}
						if (walk.getPathString().equals(repoRelativePath)) {
							inProject = true;
							if (walk.isSubtree()) {
								walk.enterSubtree();
							}
							continue;
						}
						if (walk.isSubtree()) {
							if (inProject) {
								IResource r = toRefresh
										.remove(walk.getNameString());
								if (r != null) {
									r.refreshLocal(IResource.DEPTH_INFINITE,
											progress.newChild(1));
								}
								if (toRefresh.isEmpty()) {
									return;
								}
							} else {
								walk.enterSubtree();
							}
						}
					}
				} catch (IOException e) {
					if (!toRefresh.isEmpty()) {
						logWarning(
								MessageFormat.format(
										UIText.Activator_refreshProblem, Integer
												.toString(toRefresh.size())),
								e);
						progress.setWorkRemaining(toRefresh.size());
						for (IResource r : toRefresh.values()) {
							r.refreshLocal(IResource.DEPTH_INFINITE,
									progress.newChild(1));
						}
					}
				}
			}
		}

