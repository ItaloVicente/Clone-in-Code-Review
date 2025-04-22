		final long deltaSize;

		final int hdrLen;

		final long baseOffset;

		Delta(Delta next
			this.next = next;
			this.selfOffset = ofs;
			this.deltaSize = sz;
			this.hdrLen = hdrLen;
			this.baseOffset = baseOffset;
		}

		ObjectLoader apply(ObjectLoader ldr
				throws IOException
			if (Integer.MAX_VALUE <= deltaSize || ldr.isLarge())
				return large(pack

			final byte[] base;
