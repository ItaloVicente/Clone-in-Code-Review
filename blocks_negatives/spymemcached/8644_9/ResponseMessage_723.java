	/**
	 * Gets the value of the reserved3 field if the field exists in the message.
	 * @return The reserved3 data.
	 */
	public int getReserved3() {
		if (RESERVED3_OFFSET + RESERVED3_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED3_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED3_FIELD_LENGTH);
	}
