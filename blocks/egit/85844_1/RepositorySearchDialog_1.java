	private void findGitDirsRecursive(Path root, final Set<Path> gitDirs,
			final IProgressMonitor monitor, final boolean lookForNested) {
		try {
			Files.walkFileTree(root,
					EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
					new SimpleFileVisitor<Path>() {

						long lastMonitorUpdate = System.currentTimeMillis();

						@Override
						public FileVisitResult visitFileFailed(Path file,
								IOException exc) throws IOException {
							return FileVisitResult.SKIP_SUBTREE;
						}

						@Override
						public FileVisitResult preVisitDirectory(Path d,
								BasicFileAttributes attrs) throws IOException {
							if (monitor.isCanceled()) {
								return FileVisitResult.TERMINATE;
							}
							updateMonitor(d);
							Path resolved = resolve(d);
							if ((d != null && resolved != null)
									&& !suppressed(d, resolved)) {
								gitDirs.add(resolved.toAbsolutePath());
								monitor.setTaskName(NLS.bind(
										UIText.RepositorySearchDialog_RepositoriesFound_message,
										Integer.valueOf(gitDirs.size())));
								if (resolved.getFileName().toString()
										.equals(Constants.DOT_GIT)) { // non-bare
									if (!lookForNested) {
										return FileVisitResult.SKIP_SUBTREE;
									}
								} else { // bare
									return FileVisitResult.SKIP_SUBTREE;
								}
							}
							return FileVisitResult.CONTINUE;
						}

						private void updateMonitor(Path d) {
							long now = System.currentTimeMillis();
							if ((now - lastMonitorUpdate) > 100L) {
								if (!Constants.DOT_GIT.equals(d.getFileName())) {
									monitor.subTask(d.toAbsolutePath().toString());
									lastMonitorUpdate = now;
								}
							}
						}

						private Path resolve(Path d) {
							File f = FileKey.resolve(d.toFile(), FS.DETECTED);
							if (f == null) {
								return null;
							}
							return f.toPath();
						}
					});
		} catch (IOException e) {
			Activator.error(e.getMessage(), e);
