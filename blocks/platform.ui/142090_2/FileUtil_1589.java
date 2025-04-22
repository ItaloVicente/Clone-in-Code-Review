	public static IFile createFile(String name, IProject proj)
			throws CoreException {
		IFile file = proj.getFile(name);
		if (!file.exists()) {
			String str = " ";
			InputStream in = new ByteArrayInputStream(str.getBytes());
			file.create(in, true, null);
		}
		return file;
	}
