
		final boolean extended = isExtended();
		final int len;
		if (extended) {
			len = INFO_LEN_EXTENDED;
			IO.readFully(in

			if ((getExtendedFlags() & ~EXTENDED_FLAGS) != 0)
				throw new IOException("Unrecognized extended flags: " + getExtendedFlags());
		}
		else
			len = INFO_LEN;

		infoAt[0] += len;
		md.update(info
