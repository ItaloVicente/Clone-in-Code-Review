	public List<RebaseTodoLine> readRebaseTodo(String path
			boolean includeComments)
			throws IOException {
		return new RebaseTodoFile(this).readRebaseTodo(path
	}

	public void writeRebaseTodoFile(String path
			boolean append)
			throws IOException {
		new RebaseTodoFile(this).writeRebaseTodoFile(path
	}
