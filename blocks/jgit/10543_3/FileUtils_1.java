
		boolean delete = false;
		if ((options & EMPTY_DIRECTORIES_ONLY) != 0) {
			if (f.isDirectory()) {
				delete = true;
			} else {
				if ((options & IGNORE_ERRORS) == 0)
					throw new IOException(MessageFormat.format(
							JGitText.get().deleteFileFailed
							f.getAbsolutePath()));
			}
		} else {
			delete = true;
		}

		if (delete && !f.delete()) {
