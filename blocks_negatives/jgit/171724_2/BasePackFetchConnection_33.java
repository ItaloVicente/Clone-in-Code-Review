		if (receivedReady && TransferConfig.ProtocolVersion.V2
				.equals(getProtocolVersion())) {
			String delim = pckIn.readString();
			if (!PacketLineIn.isDelimiter(delim)) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().expectedGot, "0001", delim)); //$NON-NLS-1$
			}
			return;
		}
