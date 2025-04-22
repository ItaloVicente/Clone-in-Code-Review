	public int getFlags() {
		if (FLAGS_OFFSET + FLAGS_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + FLAGS_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, FLAGS_FIELD_LENGTH);
