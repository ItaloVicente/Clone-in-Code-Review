	final PackedObjectLoader resolveBase(final WindowCursor curs, final long ofs)
			throws IOException {
		if (isCorrupt(ofs)) {
			throw new CorruptObjectException(MessageFormat.format(JGitText
					.get().objectAtHasBadZlibStream, ofs, getPackFile()));
		}
		return load(curs, ofs);
	}

