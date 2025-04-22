	/**
	 * Gets the value of the reserved1 field if the field exists in the message.
	 * @return The reserved1 data.
	 */
	public int getReserved1() {
		if (RESERVED1_OFFSET + RESERVED1_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED1_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED1_FIELD_LENGTH);
	}
