	}

	protected BaseMessage(byte[] b) {
		magic = TapMagic.getMagicByByte(b[MAGIC_OFFSET]);
		opcode = TapOpcode.getOpcodeByByte(b[OPCODE_OFFSET]);
		keylength = decodeShort(b, KEYLENGTH_OFFSET);
		extralength = b[EXTRALENGTH_OFFSET];
		datatype = b[DATATYPE_OFFSET];
		vbucket = decodeShort(b, VBUCKET_OFFSET);
		totalbody = decodeInt(b, TOTALBODY_OFFSET);
		opaque = decodeInt(b, OPAQUE_OFFSET);
		cas = decodeLong(b, CAS_OFFSET);
