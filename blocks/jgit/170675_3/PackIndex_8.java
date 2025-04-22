		return open(idxFile
	}

	public static PackIndex open(File idxFile
		try (SilentFileInputStream fd = new SilentFileInputStream(idxFile)) {
			final byte[] hdr = new byte[8];
			IO.readFully(fd
			if (isTOC(hdr)) {
				final int v = NB.decodeInt32(hdr
				switch (v) {
				case 2:
					if (useMmap) {
						fd.close();
						return new PackIndexV2m(idxFile);
					}
					return new PackIndexV2(fd);
				default:
					throw new UnsupportedPackIndexVersionException(v);
				}
			}
			return new PackIndexV1(fd
		}
		catch (IOException ioe) {
