
	private void extend(boolean extend) {
		final int lengthDelta = INFO_LEN_EXTENDED - INFO_LEN;

		if (extend) {
			final int entryEndPos = infoOffset + INFO_LEN;
			byte[] extendedInfo = new byte[info.length + lengthDelta];

			System.arraycopy(info

			System.arraycopy(info
					infoOffset + INFO_LEN_EXTENDED

			info = extendedInfo;
		} else {
			final int entryEndPos = infoOffset + INFO_LEN;
			byte[] shortenedInfo = new byte[info.length - lengthDelta];

			System.arraycopy(info

			System.arraycopy(info
					info.length - entryEndPos);

			info = shortenedInfo;
		}
	}
