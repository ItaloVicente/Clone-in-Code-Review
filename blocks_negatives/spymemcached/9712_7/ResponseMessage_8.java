	public int getReserved1() {
		if (RESERVED1_OFFSET + RESERVED1_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED1_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED1_FIELD_LENGTH);
