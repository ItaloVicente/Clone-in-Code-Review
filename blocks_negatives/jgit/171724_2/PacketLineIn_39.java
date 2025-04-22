		AckNackResult result = readACKorEOF(returnedId);
		if (result == AckNackResult.ACK_EOF) {
			throw new PackProtocolException(
					JGitText.get().expectedACKNAKFoundEOF);
		}
		return result;
	}

	AckNackResult readACKorEOF(MutableObjectId returnedId) throws IOException {
