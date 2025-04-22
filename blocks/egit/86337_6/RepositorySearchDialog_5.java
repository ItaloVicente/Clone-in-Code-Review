				private boolean isDotGit(@NonNull Path d) {
					return Constants.DOT_GIT.equals(d.getFileName().toString());
				}

				private boolean isSameFile(@NonNull Path f1, @NonNull Path f2) {
					try {
						return Files.isSameFile(f1, f2);
					} catch (IOException e) {
						return false;
					}
				}

				private boolean hasSubmodule(@NonNull Path dotGit) {
					Path gitmodules = dotGit.getParent()
							.resolve(Constants.DOT_GIT_MODULES);
					Path modules = dotGit.resolve(Constants.MODULES);
					return Files.exists(gitmodules)
							&& Files.exists(modules);
				}

				private boolean isGitInternal(@NonNull Path d) {
					Path p = d.getParent();
					String n = d.getFileName().toString();
					return p != null && isDotGit(p) && "objects".equals(n) //$NON-NLS-1$
							|| "logs".equals(n) //$NON-NLS-1$
							|| "refs".equals(n); //$NON-NLS-1$
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
			};
			Files.walkFileTree(root, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
					Integer.MAX_VALUE, visitor);
