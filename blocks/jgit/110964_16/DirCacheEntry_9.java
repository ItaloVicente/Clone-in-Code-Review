
	private void extend() {
		final int entryEndPos = infoOffset + INFO_LEN;
		byte[] extendedInfo = new byte[info.length + INFO_LEN_EXTENDED_DELTA];

		System.arraycopy(info
		System.arraycopy(info
				infoOffset + INFO_LEN_EXTENDED

		info = extendedInfo;
	}
