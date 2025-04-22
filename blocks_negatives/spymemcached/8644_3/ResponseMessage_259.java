	/**
	 * Gets the value of the flags field if the field exists in the message.
	 * @return The flags data.
	 */
	public int getFlags() {
		if (FLAGS_OFFSET + FLAGS_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + FLAGS_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, FLAGS_FIELD_LENGTH);
	}
