		if (delete) {
			Throwable t = null;
			Path p = f.toPath();
			try {
				Files.delete(p);
				return;
			} catch (FileNotFoundException e) {
				if ((options & (SKIP_MISSING | IGNORE_ERRORS)) == 0) {
					throw new IOException(MessageFormat.format(
							JGitText.get().deleteFileFailed
							f.getAbsolutePath())
				}
				return;
			} catch (IOException e) {
				t = e;
			}
			if ((options & RETRY) != 0) {
