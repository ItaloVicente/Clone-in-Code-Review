	static AckNackResult parseACKv2(String line
			throws IOException {
			return AckNackResult.NAK;
		}
			returnedId.fromString(line.substring(4
			return AckNackResult.ACK_COMMON;
		}
			return AckNackResult.ACK_READY;
		}
			throw new PackProtocolException(line.substring(4));
		}
		throw new PackProtocolException(
				MessageFormat.format(JGitText.get().expectedACKNAKGot
	}

