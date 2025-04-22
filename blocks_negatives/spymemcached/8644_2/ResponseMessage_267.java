	/**
	 * Gets the value of the key field if the field exists in the message.
	 * @return The key data.
	 */
	public String getKey() {
		if (getExtralength() >= getTotalbody()) {
			return new String();
		}
		int offset = (int) (HEADER_LENGTH + getExtralength());
		return new String(mbytes, offset, getKeylength());
	}
