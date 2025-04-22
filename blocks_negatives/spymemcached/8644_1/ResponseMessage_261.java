	/**
	 * Gets the value of the time to live field if the field exists in the message.
	 * @return The time to live value;
	 */
	public int getTTL() {
		if (TTL_OFFSET + TTL_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + TTL_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, TTL_FIELD_LENGTH);
	}
