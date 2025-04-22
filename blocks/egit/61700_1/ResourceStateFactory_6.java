						@Override
						public FileVisitResult preVisitDirectory(Path dir,
								BasicFileAttributes attrs) throws IOException {
							if (Constants.DOT_GIT.equals(dir.getFileName())) {
								return FileVisitResult.SKIP_SUBTREE;
							}
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFile(Path file,
								BasicFileAttributes attrs) throws IOException {
							if (!attrs.isDirectory()) {
								result[0] = true;
								return FileVisitResult.TERMINATE;
							}
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(Path file,
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
			return false;
