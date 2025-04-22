		if (!biDirectionalPipe) {
			int eof = rawIn.read();
			if (0 <= eof)
				throw new CorruptObjectException(MessageFormat.format(
						JGitText.get().expectedEOFReceived,
		}

