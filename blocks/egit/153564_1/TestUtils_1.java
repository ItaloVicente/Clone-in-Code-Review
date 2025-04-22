		if (rootDir.exists()) {
			try {
				FileUtils.delete(rootDir,
						FileUtils.RECURSIVE | FileUtils.RETRY);
			} catch (DirectoryNotEmptyException e) {
				System.err.println(e.toString());
				listDirectory(rootDir, true);
				throw e;
			}
		}
	}

	public static void listDirectory(File directory, boolean recursive) {
		try {
			java.nio.file.Path top = directory.toPath();
			Files.walkFileTree(top,
					new SimpleFileVisitor<java.nio.file.Path>() {

						private void print(java.nio.file.Path path,
								BasicFileAttributes attrs) {
							StringBuilder b = new StringBuilder();
							b.append(attrs.lastModifiedTime().toString());
							b.append(' ');
							b.append(path.toString());
							if (attrs.isSymbolicLink()) {
								b.append(" (symlink)");
							} else if (attrs.isDirectory()) {
								b.append('/');
							}
							System.out.println(b.toString());
						}

						@Override
						public FileVisitResult preVisitDirectory(
								java.nio.file.Path dir,
								BasicFileAttributes attrs) throws IOException {
							print(dir, attrs);
							return (recursive || top.equals(dir))
									? FileVisitResult.CONTINUE
									: FileVisitResult.SKIP_SUBTREE;
						}

						@Override
						public FileVisitResult visitFile(
								java.nio.file.Path file,
								BasicFileAttributes attrs) throws IOException {
							print(file, attrs);
							return FileVisitResult.CONTINUE;
						}
					});
		} catch (Exception e) {
			System.err.println("[ERROR] Error listing directory: " + directory);
			e.printStackTrace();
		}
