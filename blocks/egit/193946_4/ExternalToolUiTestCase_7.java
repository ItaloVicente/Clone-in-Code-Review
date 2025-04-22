	protected void writeFolderGitAttributes(String gitAttributesContents)
			throws IOException {
		writeProjectFile(FOLDER + "/.gitattributes", gitAttributesContents);
	}

	protected void writeProjectFile(String projectFilePath,
			String gitAttributesContents) throws IOException {
		java.nio.file.Path path = getProjectFilePath(projectFilePath);
		Files.write(path, gitAttributesContents.getBytes(),
				StandardOpenOption.CREATE_NEW);
		createdFiles.add(path);
	}

	protected java.nio.file.Path getProjectFilePath(String path) {
		IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1).getFile(new Path(path)).getLocation();
		java.nio.file.Path filePath = Paths
				.get(workspacePath.toFile().getAbsolutePath());
		return filePath;
	}

