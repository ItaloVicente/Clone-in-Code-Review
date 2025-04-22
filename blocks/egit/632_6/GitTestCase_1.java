	protected ObjectId createFile(Repository repository, IProject project, String name, String content) throws IOException {
		File file = new File(project.getProject().getLocation().toFile(), name);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(content);
		fileWriter.close();
		ObjectWriter objectWriter = new ObjectWriter(repository);
		return objectWriter.writeBlob(file);
	}

	protected ObjectId createFileCorruptShort(Repository repository, IProject project, String name, String content) throws IOException {
		ObjectId id = createFile(repository, project, name, content);
		File file = new File(repository.getDirectory(), "objects/" + id.name().substring(0,2) + "/" + id.name().substring(2));
		byte[] readFully = IO.readFully(file);
		file.delete();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] truncatedData = new byte[readFully.length - 1];
		System.arraycopy(readFully, 0, truncatedData, 0, truncatedData.length);
		fileOutputStream.write(truncatedData);
		fileOutputStream.close();
		return id;
	}

	protected ObjectId createEmptyTree(Repository repository) throws IOException {
		ObjectWriter objectWriter = new ObjectWriter(repository);
		Tree tree = new Tree(repository);
		return objectWriter.writeTree(tree);
	}

	protected String slurpAndClose(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			int ch;
			while ((ch = inputStream.read()) != -1) {
				stringBuilder.append((char)ch);
			}
		} finally {
			inputStream.close();
		}
		return stringBuilder.toString();
	}

