	private boolean collectProjectFilesFromDirectory(Collection<File> files,
			File directory, Set<String> directoriesVisited,
			IProgressMonitor monitor) {

		if (monitor.isCanceled()) {
			return false;
		}
		monitor.subTask(NLS.bind(UIText.RepositoriesView_Checking_Message,
				directory.getPath()));
		File[] contents = directory.listFiles();
		if (contents == null)
			return false;

		final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isFile() && file.getName().equals(dotProject)) {
				files.add(file.getParentFile());
				return true;
			}
		}
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isDirectory()) {
				if (!contents[i].getName().equals(
						GitProjectsImportPage.METADATA_FOLDER)) {
					try {
						String canonicalPath = contents[i].getCanonicalPath();
						if (!directoriesVisited.add(canonicalPath)) {
							continue;
						}
					} catch (IOException e) {
						Activator.handleError(e.getMessage(), e, false);
					}
					collectProjectFilesFromDirectory(files, contents[i],
							directoriesVisited, monitor);
				}
			}
		}
		return true;
	}

