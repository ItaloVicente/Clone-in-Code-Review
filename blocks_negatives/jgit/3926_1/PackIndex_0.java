			final byte[] hdr = new byte[8];
			IO.readFully(fd, hdr, 0, hdr.length);
			if (isTOC(hdr)) {
				final int v = NB.decodeInt32(hdr, 4);
				switch (v) {
				case 2:
					return new PackIndexV2(fd);
				default:
					throw new IOException(MessageFormat.format(JGitText.get().unsupportedPackIndexVersion, v));
				}
			}
			return new PackIndexV1(fd, hdr);
