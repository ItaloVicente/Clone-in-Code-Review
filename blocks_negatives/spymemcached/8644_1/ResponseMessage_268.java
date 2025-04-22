	/**
	 * Gets the value of the value field if the field exists in the message.
	 * @return The value data.
	 */
	public byte[] getValue() {
		if (getExtralength() + getKeylength() >= getTotalbody()) {
			return new byte[0];
		}
		int offset = (int) (HEADER_LENGTH + getExtralength() + getKeylength());
		int length = (int) (getTotalbody() - getKeylength() - getExtralength());
		byte[] value = new byte[length];
		System.arraycopy(mbytes, offset, value, 0, length);
		return value;
	}
