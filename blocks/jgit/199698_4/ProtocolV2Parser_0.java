		if (!PacketLineIn.isDelimiter(line)) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine
		}

		line = pckIn.readString();
