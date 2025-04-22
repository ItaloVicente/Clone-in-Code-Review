				@Override
				public FileVisitResult preVisitDirectory(Path d,
						BasicFileAttributes attrs) throws IOException {
					if (m.isCanceled()) {
						return FileVisitResult.TERMINATE;
					} else if (d == null) {
						return FileVisitResult.CONTINUE;
					} else if (skipHidden && Files.isHidden(d)
							&& !isDotGit(d)) {
						return FileVisitResult.SKIP_SUBTREE;
					}
					updateMonitor(d);
					Path resolved = resolve(d);
					if (resolved == null) {
						return FileVisitResult.CONTINUE;
					}
					if (!suppressed(resolved)) {
						gitDirs.add(resolved.toAbsolutePath());
						updateMonitor(resolved);
						if (isDotGit(resolved)) { // non-bare
							if (!lookForNested
									|| (isSameFile(d, resolved)
											&& !hasSubmodule(resolved))
									|| isGitInternal(resolved)) {
								return FileVisitResult.SKIP_SUBTREE;
							}
						} else { // bare
							return FileVisitResult.SKIP_SUBTREE;
						}
					}
					return FileVisitResult.CONTINUE;
