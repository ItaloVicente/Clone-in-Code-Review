	public static boolean findProjectFiles(final Collection<File> files,
			final File directory, final Set<String> visistedDirs,
			final IProgressMonitor monitor) {
		IProgressMonitor pm = monitor;
		if (pm == null)
			pm = new NullProgressMonitor();
		else if (pm.isCanceled())
			return false;

		monitor.subTask(NLS.bind(CoreText.ProjectUtil_taskCheckingDirectory,
				directory.getPath()));

		final File[] contents = directory.listFiles();
		if (contents == null || contents.length == 0)
			return false;

		Set<String> directoriesVisited;
		if (visistedDirs == null) {
			directoriesVisited = new HashSet<String>();
			try {
				directoriesVisited.add(directory.getCanonicalPath());
			} catch (IOException exception) {
				Activator.logError(exception.getLocalizedMessage(), exception);
			}
		} else
			directoriesVisited = visistedDirs;

		final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isFile() && file.getName().equals(dotProject)) {
				files.add(file);
				return true;
			}
		}
		for (int i = 0; i < contents.length; i++) {
			if (!contents[i].isDirectory())
				continue;
			if (contents[i].getName().equals(METADATA_FOLDER))
				continue;
			try {
				String canonicalPath = contents[i].getCanonicalPath();
				if (!directoriesVisited.add(canonicalPath)) {
					continue;
				}
			} catch (IOException exception) {
				Activator.logError(exception.getLocalizedMessage(), exception);

			}
			findProjectFiles(files, contents[i], directoriesVisited, pm);
		}
		return true;
	}
