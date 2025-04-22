			File directory, Set<String> visistedDirs,
			IProgressMonitor monitor) {

		Set<String> directoriesVisited;

		if (monitor.isCanceled()) {
			return false;
		}
		monitor.subTask(NLS.bind(
				UIText.WizardProjectsImportPage_CheckingMessage, directory
						.getPath()));
		File[] contents = directory.listFiles();
		if (contents == null)
			return false;

		if (visistedDirs == null) {
			directoriesVisited = new HashSet<String>();
			try {
				directoriesVisited.add(directory.getCanonicalPath());
			} catch (IOException exception) {
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, Activator.getPluginId(),
								exception.getLocalizedMessage(), exception));
			}
		} else {
			directoriesVisited = visistedDirs;
		}

		final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isFile() && file.getName().equals(dotProject)) {
				files.add(file);
				return true;
			}
		}
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isDirectory()) {
				if (!contents[i].getName().equals(METADATA_FOLDER)) {
					try {
						String canonicalPath = contents[i].getCanonicalPath();
						if (!directoriesVisited.add(canonicalPath)) {
							continue;
						}
					} catch (IOException exception) {
						StatusManager.getManager().handle(
								new Status(IStatus.ERROR, Activator
										.getPluginId(), exception
										.getLocalizedMessage(), exception));

					}
					collectProjectFilesFromDirectory(files, contents[i],
							directoriesVisited, monitor);
				}
			}
		}
		return true;
