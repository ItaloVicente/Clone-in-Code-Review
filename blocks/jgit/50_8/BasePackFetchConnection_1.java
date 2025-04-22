	private void markCommon(final RevObject obj
			throws IOException {
		if (statelessRPC && anr == AckNackResult.ACK_COMMON && !obj.has(COMMON)) {
			StringBuilder s;

			s = new StringBuilder(6 + Constants.OBJECT_ID_STRING_LENGTH);
			s.append(obj.name());
			s.append('\n');
			pckState.writeString(s.toString());
		}
