		return TapOpcode.getOpcodeByByte(mbytes[OPCODE_INDEX]);
	}

	/**
	 * Sets the key length for this message. This function should never be called by
	 * the user since changes to fields that affect key length call this function
	 * automatically.
	 * @param l The new value for the key length field.
	 */
	protected final void setKeylength(long l) {
		Util.valueToFieldOffest(mbytes, KEY_LENGTH_INDEX, KEY_LENGTH_FIELD_LENGTH, l);
