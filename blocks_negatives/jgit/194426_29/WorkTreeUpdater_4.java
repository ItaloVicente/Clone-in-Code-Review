	private ObjectId insertResult(StreamLoader resultStreamLoader, Attribute lfsAttribute)
			throws IOException {
		try (LfsInputStream is =
				org.eclipse.jgit.util.LfsFactory.getInstance()
						.applyCleanFilter(
								repo,
								resultStreamLoader.data.load(),
								resultStreamLoader.size,
								lfsAttribute)) {
