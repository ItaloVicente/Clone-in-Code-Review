
		final int len;
		if (isExtended()) {
			len = INFO_LEN_EXTENDED;
			IO.readFully(in

			if ((getExtendedFlags() & ~EXTENDED_FLAGS) != 0)
				throw new IOException(MessageFormat.format(JGitText.get()
						.DIRCUnrecognizedExtendedFlags
		}
		else
			len = INFO_LEN;

		infoAt.value += len;
		md.update(info
