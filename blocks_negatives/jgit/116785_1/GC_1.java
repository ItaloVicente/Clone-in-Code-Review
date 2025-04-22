		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
				new DirectoryStream.Filter<Path>() {

					@Override
					public boolean accept(Path file) throws IOException {
						Path fileName = file.getFileName();
						return Files.isRegularFile(file) && fileName != null
								&& PATTERN_LOOSE_OBJECT
										.matcher(fileName.toString()).matches();
					}
