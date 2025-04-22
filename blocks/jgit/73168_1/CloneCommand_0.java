			if (!directory.isDirectory()) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().initFailedDirIsNoDirectory
			}
			if (gitDir != null && !gitDir.isDirectory()) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().initFailedGitDirIsNoDirectory
						gitDir));
			}
