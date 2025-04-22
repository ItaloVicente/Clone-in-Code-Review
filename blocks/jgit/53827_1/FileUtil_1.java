	public static void delete(final Path path
			throws IOException {
		if (!Files.exists(path
			return;
		}
		try {
			Files.delete(path);
		} catch (DirectoryNotEmptyException e) {
			if (!recursive) {
				throw e;
			}
			Files.walkFileTree(path
				@Override
				public FileVisitResult visitFile(Path file
						BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir
						IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		}
	}

