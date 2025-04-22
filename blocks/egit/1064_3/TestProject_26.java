	public IFile createFile(String name, byte[] content) throws Exception {
		IFile file = project.getFile(name);
		InputStream inputStream = new ByteArrayInputStream(content);
		file.create(inputStream, true, null);

		return file;
	}

	public IFolder createFolder(String name) throws Exception {
		IFolder folder = project.getFolder(name);
		folder.create(true, true, null);

		IFile keep = project.getFile(name + "/keep");
		keep.create(new ByteArrayInputStream(new byte[] {0}), true, null);

		return folder;
	}

