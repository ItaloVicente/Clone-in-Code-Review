		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				Map<String, String> updates = null;
				try {
					SubmoduleSyncCommand sync = Git.wrap(repository)
							.submoduleSync();
					for (String path : paths) {
						sync.addPath(path);
					}
					updates = sync.call();
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} finally {
					if (updates != null && !updates.isEmpty()) {
						repository.notifyIndexChanged(true);
					}
