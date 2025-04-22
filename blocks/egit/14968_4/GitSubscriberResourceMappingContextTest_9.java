	private void setContents(IFile targetFile, String newContents)
			throws CoreException {
		targetFile.setContents(
				new ByteArrayInputStream(newContents.getBytes()),
				IResource.FORCE, new NullProgressMonitor());
	}

