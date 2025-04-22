
	protected ObjectId createEmptyTree(Repository repository) throws IOException {
		ObjectWriter objectWriter = new ObjectWriter(repository);
		Tree tree = new Tree(repository);
		return objectWriter.writeTree(tree);
	}

