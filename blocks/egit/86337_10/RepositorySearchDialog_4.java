				private boolean isGitInternal(@NonNull Path d) {
					Path p = d.getParent();
					String n = d.getFileName().toString();
					return p != null && isDotGit(p)
							&& !Constants.MODULES.equals(n);
				}
