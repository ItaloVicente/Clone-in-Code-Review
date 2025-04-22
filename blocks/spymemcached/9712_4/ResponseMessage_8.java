	public ResponseMessage(byte[] b) {
		magic = TapMagic.getMagicByByte(b[MAGIC_OFFSET]);
		opcode = TapOpcode.getOpcodeByByte(b[OPCODE_OFFSET]);
		keylength = decodeShort(b, KEYLENGTH_OFFSET);
		extralength = b[EXTRALENGTH_OFFSET];
		datatype = b[DATATYPE_OFFSET];
		vbucket = decodeShort(b, VBUCKET_OFFSET);
		totalbody = decodeInt(b, TOTALBODY_OFFSET);
		opaque = decodeInt(b, OPAQUE_OFFSET);
		cas = decodeLong(b, CAS_OFFSET);
		engineprivate = decodeShort(b, ENGINE_PRIVATE_OFFSET);
		flags = TapFlag.getFlags(decodeShort(b, FLAGS_OFFSET));
		ttl = b[TTL_OFFSET];
		reserved1 = b[RESERVED1_OFFSET];
		reserved2 = b[RESERVED2_OFFSET];
		reserved3 = b[RESERVED3_OFFSET];
		if (!opcode.equals(TapOpcode.OPAQUE)) {
			if (opcode.equals(TapOpcode.MUTATION)) {
				itemflags = decodeInt(b, ITEM_FLAGS_OFFSET);
				itemexpiry = decodeInt(b, ITEM_EXPIRY_OFFSET);
			} else {
				itemflags = 0;
				itemexpiry = 0;
			}
			byte[] keybytes = new byte[keylength];
			System.arraycopy(b, KEY_OFFSET, keybytes, 0, keylength);
			key = new String(keybytes);
			value = new byte[b.length - keylength - KEY_OFFSET];
			System.arraycopy(b, (keylength + KEY_OFFSET), value, 0, value.length);
		} else {
			itemflags = 0;
			itemexpiry = 0;
			key = null;
			value = null;
		}
