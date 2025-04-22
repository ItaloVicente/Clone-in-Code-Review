			if (directory.exists() && !directory.isDirectory()) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().initFailedDirIsNoDirectory
			}
			if (gitDir != null && gitDir.exists() && !gitDir.isDirectory()) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().initFailedGitDirIsNoDirectory
						gitDir));
			}
