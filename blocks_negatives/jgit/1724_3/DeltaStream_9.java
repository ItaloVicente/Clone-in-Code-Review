
	private void seekBase() throws IOException {
		if (baseStream == null) {
			baseStream = openBase();
			if (getBaseSize() != baseSize)
				throw new CorruptObjectException(
						JGitText.get().baseLengthIncorrect);
			IO.skipFully(baseStream, copyOffset);
			baseOffset = copyOffset;

		} else if (baseOffset < copyOffset) {
			IO.skipFully(baseStream, copyOffset - baseOffset);
			baseOffset = copyOffset;

		} else if (baseOffset > copyOffset) {
			baseStream.close();
			baseStream = openBase();
			IO.skipFully(baseStream, copyOffset);
			baseOffset = copyOffset;
		}
	}
