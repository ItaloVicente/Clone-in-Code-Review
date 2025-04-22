	private List<Repository> getRepositoriesFromDirs(IProgressMonitor monitor)
			throws InterruptedException {

		List<String> gitDirStrings = getDirs();
		List<Repository> input = new ArrayList<Repository>();
		for (String dirString : gitDirStrings) {
			if (monitor.isCanceled()) {
				throw new InterruptedException(
						UIText.RepositoriesView_ActionCanceled_Message);
			}
			try {
				File dir = new File(dirString);
				if (dir.exists() && dir.isDirectory()) {
					input.add(new Repository(dir));
				}
			} catch (IOException e) {
				IStatus error = new Status(IStatus.ERROR, Activator
						.getPluginId(), e.getMessage(), e);
				Activator.getDefault().getLog().log(error);
			}
		}
		return input;
	}

