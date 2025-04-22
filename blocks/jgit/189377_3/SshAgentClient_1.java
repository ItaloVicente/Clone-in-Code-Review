	private static PublicKey readKey(Buffer buffer) throws BufferException {
		int endOfBuffer = buffer.wpos();
		int keyLength = buffer.getInt();
		int afterKey = buffer.rpos() + keyLength;
		if (keyLength <= 0 || afterKey > endOfBuffer) {
			throw new BufferException(
					MessageFormat.format(SshdText.get().sshAgentWrongKeyLength
							Integer.toString(keyLength)
							Integer.toString(buffer.rpos())
							Integer.toString(endOfBuffer)));
		}
		buffer.wpos(afterKey);
		try {
			return buffer.getRawPublicKey(BufferPublicKeyParser.DEFAULT);
		} catch (Exception e) {
			LOG.warn(SshdText.get().sshAgentUnknownKey
			return null;
		} finally {
			buffer.wpos(endOfBuffer);
			buffer.rpos(afterKey);
		}
	}

