		IPath workingDirPath;
		try {
			Repository repo = Activator.getDefault().getRepositoryCache()
					.lookupRepository(repositoryDir);
			if (repo.isBare()) {
				return;
			}
			workingDirPath = new Path(repo.getWorkTree().getAbsolutePath());
		} catch (IOException e) {
			org.eclipse.egit.ui.Activator.logError(e.getLocalizedMessage(), e);
			return;
		}
