				if (bare) {
					if (gitDir != null && !gitDir.equals(directory))
						throw new IllegalStateException(MessageFormat.format(
								JGitText.get().initFailedBareRepoDifferentDirs
								gitDir
					builder.setGitDir(directory);
				} else {
					if (gitDir != null && gitDir.equals(directory))
						throw new IllegalStateException(MessageFormat.format(
								JGitText.get().initFailedNonBareRepoSameDirs
								gitDir
					builder.setWorkTree(directory);
					if (gitDir == null)
						builder.setGitDir(new File(directory
				}
