		if (backup) {
			File refsOld = new File(getDirectory(), "refs.old"); //$NON-NLS-1$
			if (refsOld.exists()) {
				throw new IOException(MessageFormat.format(
					JGitText.get().fileAlreadyExists,
			}
			FileUtils.rename(refsFile, refsOld);
		} else {
			refsFile.delete();
		}
