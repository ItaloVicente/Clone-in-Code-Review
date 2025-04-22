	@Override
	public Entry[] list(File directory
		List<Entry> result = new ArrayList<>();
		FS fs = this;
		boolean checkExecutable = fs.supportsExecute();
		try {
			Files.walkFileTree(directory.toPath()
					EnumSet.noneOf(FileVisitOption.class)
					new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file
								BasicFileAttributes attrs) throws IOException {
							File f = file.toFile();
							FS.Attributes attributes = new FS.Attributes(fs
									true
									checkExecutable && f.canExecute()
									attrs.isSymbolicLink()
									attrs.isRegularFile()
									attrs.creationTime().toMillis()
									attrs.lastModifiedTime().toMillis()
									attrs.size());
							result.add(new FileEntry(f
									fileModeStrategy));
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(Path file
								IOException exc) throws IOException {
							return FileVisitResult.CONTINUE;
						}
					});
		} catch (IOException e) {
		}
		if (result.isEmpty()) {
			return NO_ENTRIES;
		}
		return result.toArray(new Entry[result.size()]);
	}

