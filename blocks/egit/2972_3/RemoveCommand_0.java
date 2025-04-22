	private void deleteProjects(
			final boolean delete,
			final List<IProject> projectsToDelete,
			IProgressMonitor monitor) {
		IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

			public void run(IProgressMonitor actMonitor)
			throws CoreException {

				for (IProject prj : projectsToDelete)
					prj.delete(delete, false, actMonitor);
			}
		};

		try {
			ResourcesPlugin.getWorkspace().run(wsr,
					ResourcesPlugin.getWorkspace().getRoot(),
					IWorkspace.AVOID_UPDATE, monitor);
		} catch (CoreException e1) {
			Activator.logError(e1.getMessage(), e1);
		}
	}

	private void deleteRepositoryContent(
			final List<RepositoryNode> selectedNodes,
			final boolean deleteWorkDir) throws IOException {
		for (RepositoryNode node : selectedNodes) {
			Repository repo = node.getRepository();
			if (!repo.isBare() && deleteWorkDir) {
				File[] files = repo.getWorkTree().listFiles();
				if (files != null)
					for (File file : files) {
						if (isTracked(file, repo))
							FileUtils.delete(file,
									FileUtils.RECURSIVE | FileUtils.RETRY);
					}
			}
			repo.close();
			FileUtils.delete(repo.getDirectory(),
					FileUtils.RECURSIVE | FileUtils.RETRY
							| FileUtils.SKIP_MISSING);
		}
	}

	private boolean isTracked(File file, Repository repo) throws IOException {
		ObjectId objectId = repo.resolve(Constants.HEAD);
		RevTree tree;
		if (objectId != null)
			tree = new RevWalk(repo).parseTree(objectId);
		else
			tree = null;

		TreeWalk treeWalk = new TreeWalk(repo);
		treeWalk.setRecursive(true);
		if (tree != null)
			treeWalk.addTree(tree);
		else
			treeWalk.addTree(new EmptyTreeIterator());
		treeWalk.addTree(new DirCacheIterator(repo.readDirCache()));
		treeWalk.setFilter(PathFilterGroup.createFromStrings(Collections.singleton(
				Repository.stripWorkDir(repo.getWorkTree(), file))));
		return treeWalk.next();

	}

