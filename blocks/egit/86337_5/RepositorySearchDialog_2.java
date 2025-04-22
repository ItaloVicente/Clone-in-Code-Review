			final SubMonitor m = SubMonitor.convert(monitor);
			Files.walkFileTree(root,
					EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
					new SimpleFileVisitor<Path>() {

						private long lastMonitorUpdate;

						@Override
						public FileVisitResult visitFileFailed(Path file,
								IOException exc) throws IOException {
							return FileVisitResult.SKIP_SUBTREE;
						}

						@Override
						public FileVisitResult preVisitDirectory(Path d,
								BasicFileAttributes attrs) throws IOException {
							if (m.isCanceled()) {
								return FileVisitResult.TERMINATE;
							} else if (d == null) {
								return FileVisitResult.CONTINUE;
							} else if (skipHidden && Files.isHidden(d)) {
								return FileVisitResult.SKIP_SUBTREE;
							}
							updateMonitor(d);
							Path resolved = resolve(d);
							if (resolved == null) {
								return FileVisitResult.CONTINUE;
							}
							if (!suppressed(d, resolved)) {
								gitDirs.add(resolved.toAbsolutePath());
								updateMonitor(d);
								if (isDotGit(resolved)) { // non-bare
									if (!lookForNested) {
										return FileVisitResult.SKIP_SUBTREE;
									}
								} else { // bare
									return FileVisitResult.SKIP_SUBTREE;
								}
							}
							return FileVisitResult.CONTINUE;
						}

						private Path resolve(@NonNull Path d) {
							File f = FileKey.resolve(d.toFile(), FS.DETECTED);
							if (f == null) {
								return null;
							}
							return f.toPath();
						}

						private boolean suppressed(@NonNull Path d,
								@NonNull Path resolved) {
							return !allowBare && !isDotGit(resolved)
									&& isSameFile(d, resolved);
						}

						private boolean isDotGit(@NonNull Path f) {
							return Constants.DOT_GIT
									.equals(f.getFileName().toString());
						}

						private boolean isSameFile(@NonNull Path f1,
								@NonNull Path f2) {
							try {
								return Files.isSameFile(f1, f2);
							} catch (IOException e) {
								return false;
							}
						}

						private void updateMonitor(@NonNull Path d) {
							long now = System.currentTimeMillis();
							if ((now - lastMonitorUpdate) > 100L) {
								m.setWorkRemaining(100);
								m.worked(1);
								m.setTaskName(NLS.bind(
										UIText.RepositorySearchDialog_RepositoriesFound_message,
										Integer.valueOf(gitDirs.size()),
										d.toAbsolutePath().toString()));
								lastMonitorUpdate = now;
							}
						}
					});
