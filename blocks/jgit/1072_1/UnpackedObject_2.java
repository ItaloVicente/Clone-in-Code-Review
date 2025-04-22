	private static void checkValidEndOfStream(InputStream in
			AnyObjectId id
			CorruptObjectException {
		for (;;) {
			int r;
			try {
				r = inf.inflate(buf);
			} catch (DataFormatException e) {
				throw new CorruptObjectException(id
						JGitText.get().corruptObjectBadStream);
			}
			if (r != 0)
				throw new CorruptObjectException(id
						JGitText.get().corruptObjectIncorrectLength);

			if (inf.finished()) {
				if (inf.getRemaining() != 0 || in.read() != -1)
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectBadStream);
				break;
			}

			if (!inf.needsInput())
				throw new CorruptObjectException(id
						JGitText.get().corruptObjectBadStream);

			r = in.read(buf);
			if (r <= 0)
				throw new CorruptObjectException(id
						JGitText.get().corruptObjectBadStream);
			inf.setInput(buf
		}
	}

