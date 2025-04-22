	protected static IFile touch(final String newContent) throws Exception {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);
		if (!prj.isAccessible())
			throw new IllegalStateException("No project to touch");
		IFile file = prj.getFile(new Path("folder/test.txt"));
		file.setContents(
				new ByteArrayInputStream(newContent.getBytes(prj
						.getDefaultCharset())), 0, null);
		return file;
	}

