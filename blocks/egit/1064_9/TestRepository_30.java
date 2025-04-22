	public File createFile(IProject project, String name) throws IOException {
		String path = project.getLocation().append(name).toOSString();
		int lastSeparator = path.lastIndexOf(File.separator);
		new File(path.substring(0, lastSeparator)).mkdirs();

		File file = new File(path);
		file.createNewFile();

		return file;
	}

	public RevCommit addAndCommit(IProject project, File file, String commitMessage)
			throws Exception {
		track(file);
		addToIndex(project, file);

		return commit(commitMessage);
	}

	public RevCommit appendContentAndCommit(IProject project, File file,
			byte[] content, String commitMessage) throws Exception {
		return appendContentAndCommit(project, file, new String(content),
				commitMessage);
	}

	public RevCommit appendContentAndCommit(IProject project, File file,
			String content, String commitMessage) throws Exception {
		appendFileContent(file, content);
		track(file);
		addToIndex(project, file);

		return commit(commitMessage);
	}

