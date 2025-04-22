
		if (bAvail != 0)
			throw new CorruptObjectException(MessageFormat.format(
					JGitText.get().expectedEOFReceived
					"\\x" + Integer.toHexString(buf[bOffset] & 0xff)));

		if (isCheckEofAfterPackFooter()) {
			int eof = in.read();
			if (0 <= eof)
				throw new CorruptObjectException(MessageFormat.format(
						JGitText.get().expectedEOFReceived
						"\\x" + Integer.toHexString(eof)));
		}

