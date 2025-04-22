	private boolean isValidRepo(final Repository repository) {
		return repository != null
				&& !repository.isBare()
				&& repository.getWorkTree().exists()
				&& org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryUtil().contains(repository);
	}

	private void asyncReload(final Repository repository) {
		asyncExec(new Runnable() {

			public void run() {
				reload(repository);
			}
		});
	}

