	public IFile changeContentOfFile(IProject project, IFile file, String newContent)
			throws Exception {
		file.setContents(new ByteArrayInputStream(newContent.getBytes(project
				.getDefaultCharset())), 0, null);
		return file;
	}

