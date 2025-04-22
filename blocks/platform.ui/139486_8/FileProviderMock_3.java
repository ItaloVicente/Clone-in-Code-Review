	@Override
	public DirectoryStream<Path> newDirectoryStream(String dir, String glob) throws IOException {
		System.out.println("newDirectoryStream?" + dir + " " + glob);
		return new DirectoryStream<Path>() {

			@Override
			public void close() throws IOException {
			}

			@Override
			public Iterator<Path> iterator() {
				return newDirectoryStreamAnswers.get(dir).get(glob).stream().map(name -> Paths.get(name)).iterator();
			}
		};
	}

