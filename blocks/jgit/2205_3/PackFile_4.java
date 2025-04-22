		final long deltaSize;

		final int hdrLen;

		final long basePos;

		Delta(Delta next
			this.next = next;
			this.deltaPos = ofs;
			this.deltaSize = sz;
			this.hdrLen = hdrLen;
			this.basePos = baseOffset;
		}

		byte[] apply(byte[] base
				throws IOException
			final byte[] delta;
