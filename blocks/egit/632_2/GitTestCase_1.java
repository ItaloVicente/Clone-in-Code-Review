	protected ObjectId createFile(Repository repository, IProject project, String name, String content) throws IOException {
		File file = new File(project.getProject().getLocation().toFile(), name);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(content);
		fileWriter.close();
		ObjectWriter objectWriter = new ObjectWriter(repository);
		return objectWriter.writeBlob(file);
	}

	protected ObjectId createFileCorruptShort(Repository repository, IProject project, String name, String content) throws IOException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Method setWritable = File.class.getMethod("setWritable", new Class[] { boolean.class });
		ObjectId id = createFile(repository, project, name, content);
		File file = new File(repository.getDirectory(), "objects" + id.name().substring(0,2) + "/" + id.name().substring(3));
		setWritable.invoke(file, new Object[] { Boolean.TRUE });
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		randomAccessFile.setLength(file.length()-1);
		randomAccessFile.close();
		return id;
	}
	protected ObjectId createEmptyTree(Repository repository) throws IOException {
		ObjectWriter objectWriter = new ObjectWriter(repository);
		Tree tree = new Tree(repository);
		return objectWriter.writeTree(tree);
	}
