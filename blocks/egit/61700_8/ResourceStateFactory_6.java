				final boolean[] result = new boolean[] { false };
				final Path dotGit = Paths.get(Constants.DOT_GIT);
				Files.walkFileTree(file.toPath(), new FileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(Path dir,
							BasicFileAttributes attrs) throws IOException {
						if (dotGit.equals(dir.getFileName())) {
							return FileVisitResult.SKIP_SUBTREE;
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path path,
							BasicFileAttributes attrs) throws IOException {
						if (!attrs.isDirectory()) {
							result[0] = true;
							return FileVisitResult.TERMINATE;
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path path,
							IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir,
							IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
				});
				return result[0];
			} catch (IOException e) {
