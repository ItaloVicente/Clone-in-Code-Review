	private static void skipFully(Reader fd
		while (toSkip > 0) {
			long r = fd.skip(toSkip);
			if (r <= 0) {
				throw new EOFException(JGitText.get().shortSkipOfBlock);
			}
			toSkip -= r;
		}
	}

