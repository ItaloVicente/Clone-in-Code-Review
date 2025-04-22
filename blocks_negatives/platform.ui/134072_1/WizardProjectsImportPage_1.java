		final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
		List<File> directories = new ArrayList<>();
		for (File file : contents) {
			if(file.isDirectory()){
				directories.add(file);
			} else if (file.getName().equals(dotProject) && file.isFile()) {
				files.add(file);
				if (!nestedProjects) {
					return true;
				}
			}
		}
		for (File dir : directories) {
			if (!dir.getName().equals(METADATA_FOLDER)) {
				try {
					String canonicalPath = dir.getCanonicalPath();
					if (!directoriesVisited.add(canonicalPath)) {
						continue;
					}
				} catch (IOException exception) {
					StatusManager.getManager().handle(StatusUtil.newError(exception));
