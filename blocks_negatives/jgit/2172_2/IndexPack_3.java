		final int crc32 = (int) crc.getValue();
		if (oldCRC != crc32)
			throw new IOException(MessageFormat.format(JGitText.get().corruptionDetectedReReadingAt, pos));
		if (oe == null) {
			objectDigest.update(Constants.encodedTypeString(type));
			objectDigest.update((byte) ' ');
			objectDigest.update(Constants.encodeASCII(data.length));
			objectDigest.update((byte) 0);
			objectDigest.update(data);
			tempObjectId.fromRaw(objectDigest.digest(), 0);
