	private void deleteOrphans() {
		Path packDir = Paths.get(repo.getObjectsDirectory().getAbsolutePath()

		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) throws IOException {
				String name = file.toFile().getName();

										+ PackExt.BITMAP_INDEX.getExtension())
								|| name.endsWith(
			}
		};

		try {
			Stream<Path> pathStream = StreamSupport.stream(
					Files.newDirectoryStream(packDir
					false);
			List<String> names = pathStream
					.map(path -> path.toFile().getName())
					.collect(Collectors.toList());

			names.stream().filter(name -> isOrphan(name
					.forEach(name -> deleteQuietly(packDir.resolve(name)));
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}

	}

	private void deleteQuietly(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

	private boolean isOrphan(String fileName
				&& !fileNames.contains(
						fileName.substring(0
								+ PackExt.PACK.getExtension());
	}

