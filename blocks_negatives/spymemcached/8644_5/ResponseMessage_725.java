	/**
	 * Gets the value of the item expiry field if the field exists in the message.
	 * @return The item expiry data.
	 */
	public long getItemExpiry() {
		if (ITEM_EXPIRY_OFFSET + ITEM_EXPIRY_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + ITEM_EXPIRY_OFFSET;
		return Util.fieldToValue(mbytes, offset, ITEM_EXPIRY_FIELD_LENGTH);
	}
