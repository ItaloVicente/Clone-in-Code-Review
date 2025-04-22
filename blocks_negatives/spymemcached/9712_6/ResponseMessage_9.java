	public int getReserved2() {
		if (RESERVED2_OFFSET + RESERVED2_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED2_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED2_FIELD_LENGTH);
