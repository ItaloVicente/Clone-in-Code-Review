		String ceilingDirectoriesVar = SystemReader.getInstance().getenv(
				Constants.GIT_CEILING_DIRECTORIES_KEY);
		if (ceilingDirectoriesVar != null) {
			ceilingDirectories.addAll(Arrays.asList(ceilingDirectoriesVar
					.split(File.pathSeparator)));
		}
