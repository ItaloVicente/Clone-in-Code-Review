
	public static class PackIndexFactory {
		public PackIndex open(File idxFile) throws IOException {
			try (SilentFileInputStream fd = new SilentFileInputStream(
					idxFile)) {
				return read(fd);
			} catch (IOException ioe) {
				throw new IOException(
						MessageFormat.format(JGitText.get().unreadablePackIndex
								idxFile.getAbsolutePath())
						ioe);
			}
		}

		public PackIndex read(InputStream fd) throws IOException
				CorruptObjectException {
			final byte[] hdr = new byte[8];
			IO.readFully(fd
			if (isTOC(hdr)) {
				final int v = NB.decodeInt32(hdr
				switch (v) {
					case 2:
						return new PackIndexV2(fd);
					default:
						throw new UnsupportedPackIndexVersionException(v);
				}
			}
			return new PackIndexV1(fd
		}
	}
