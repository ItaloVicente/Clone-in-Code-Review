	@Override
	public String getFilePath(URL url) {
		return urlTosFilePaths.get(url);
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(String dir, String glob) throws IOException {
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

