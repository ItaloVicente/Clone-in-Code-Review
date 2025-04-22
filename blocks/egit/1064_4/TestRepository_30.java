	public IFile getIFile(IProject project, File file) throws CoreException {
		String relativePath = getRepoRelativePath(file.getAbsolutePath());

		String quotedProjectName = Pattern.quote(project.getName());
		relativePath = relativePath.replaceFirst(quotedProjectName, "");

		IFile iFile = project.getFile(relativePath);
		iFile.refreshLocal(0, null);

		return iFile;
	}

