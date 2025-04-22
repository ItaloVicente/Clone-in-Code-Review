	public static List<RepositoryTreeNode<Repository>> getRepositoriesFromDirs(
			IProgressMonitor monitor) throws InterruptedException {

		List<String> gitDirStrings = getDirs();
		List<RepositoryTreeNode<Repository>> input = new ArrayList<RepositoryTreeNode<Repository>>();

		for (String dirString : gitDirStrings) {
			if (monitor != null && monitor.isCanceled()) {
				throw new InterruptedException(
						UIText.RepositoriesView_ActionCanceled_Message);
			}
			try {
				File dir = new File(dirString);
				if (dir.exists() && dir.isDirectory()) {
					Repository repo = new Repository(dir);
					RepositoryTreeNode<Repository> node = new RepositoryTreeNode<Repository>(
							null, RepositoryTreeNodeType.REPO, repo, repo);
					input.add(node);
				}
			} catch (IOException e) {
				IStatus error = new Status(IStatus.ERROR, Activator
						.getPluginId(), e.getMessage(), e);
				Activator.getDefault().getLog().log(error);
			}
		}
		Collections.sort(input);
		return input;
	}

