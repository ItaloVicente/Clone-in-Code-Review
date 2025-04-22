		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				final SubmoduleAddCommand add = Git.wrap(repo).submoduleAdd();
				add.setProgressMonitor(new EclipseGitProgressTransformer(pm));
				add.setPath(path);
				add.setURI(uri);
				try {
					Repository subRepo = add.call();
					if (subRepo != null) {
						subRepo.close();
						repo.notifyIndexChanged(true);
					}
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
