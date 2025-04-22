			}
			final File dotDir = new File(directory
			if (dotDir.isFile()) {
				try {
					File refDir = fs.getSymRef(directory
					if (refDir != null && isGitRepository(refDir
						return refDir;
					}
				} catch (IOException ignored) {
				}
			} else if (isGitRepository(dotDir
				return dotDir;
			}
			final File bareDir = new File(directory.getParentFile()
					directory.getName() + Constants.DOT_GIT_EXT);
			if (isGitRepository(bareDir
				return bareDir;
			}
