		int toRemove = 0;
		if (version == DirCacheVersion.DIRC_VERSION_PATHCOMPRESS) {
			int b = in.read();
			md.update((byte) b);
			toRemove = b & 0x7F;
			while ((b & 0x80) != 0) {
				toRemove++;
				b = in.read();
				md.update((byte) b);
				toRemove = (toRemove << 7) | (b & 0x7F);
			}
			if (toRemove < 0
					|| previous != null && toRemove > previous.path.length) {
				if (previous == null) {
					throw new IOException(MessageFormat.format(
							JGitText.get().DIRCCorruptLengthFirst
							Integer.valueOf(toRemove)));
				}
				throw new IOException(MessageFormat.format(
						JGitText.get().DIRCCorruptLength
						Integer.valueOf(toRemove)
			}
		}
