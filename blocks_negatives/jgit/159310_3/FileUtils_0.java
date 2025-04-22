			try {
				Files.delete(p);
				return;
			} catch (FileNotFoundException e) {
				if ((options & (SKIP_MISSING | IGNORE_ERRORS)) == 0) {
					throw new IOException(MessageFormat.format(
							JGitText.get().deleteFileFailed,
							f.getAbsolutePath()), e);
