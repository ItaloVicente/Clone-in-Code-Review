	public File getTempDir(String name) throws IOException {
		File userHome = FS.DETECTED.userHome();
		File rootDir = new File(userHome, "EGitCoreTestTempDir");
		File result = new File(rootDir, name);
		if (result.exists())
			deleteRecursive(result);
		return result;
	}

	public void deleteTempDirs() throws IOException {
		File userHome = FS.DETECTED.userHome();
		File rootDir = new File(userHome, "EGitCoreTestTempDir");
		if (rootDir.exists())
			deleteRecursive(rootDir);
	}

	public String slurpAndClose(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			int ch;
			while ((ch = inputStream.read()) != -1) {
				stringBuilder.append((char) ch);
			}
		} finally {
			inputStream.close();
		}
		return stringBuilder.toString();
	}

	public IFile addFileToProject(IProject project, String path, String content)
			throws Exception {
		IPath filePath = new Path(path);
		IFolder folder = null;
		for (int i = 0; i < filePath.segmentCount() - 1; i++) {
			if (folder == null) {
				folder = project.getFolder(filePath.segment(i));
			} else {
				folder = folder.getFolder(filePath.segment(i));
			}
			folder.create(false, true, null);
		}
		IFile file = project.getFile(filePath);
		file.create(new ByteArrayInputStream(content.getBytes(project
				.getDefaultCharset())), true, null);
		return file;
	}

	public IProject createProjectInLocalFileSystem(File parentFile,
			String projectName) throws Exception {
		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		if (firstProject.exists())
			firstProject.delete(false, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(projectName);
		desc.setLocation(new Path(new File(parentFile, projectName).getPath()));
		firstProject.create(desc, null);
		firstProject.open(null);
		return firstProject;
	}
