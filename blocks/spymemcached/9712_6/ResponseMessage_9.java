	public ResponseMessage(byte[] b) {
		super(b);
		if (!opcode.equals(TapOpcode.NOOP)) {
			engineprivate = decodeShort(b, ENGINE_PRIVATE_OFFSET);
			flags = TapFlag.getFlags(decodeShort(b, FLAGS_OFFSET));
			ttl = b[TTL_OFFSET];
			reserved1 = b[RESERVED1_OFFSET];
			reserved2 = b[RESERVED2_OFFSET];
			reserved3 = b[RESERVED3_OFFSET];
		} else {
			engineprivate = 0;
			flags = new LinkedList<TapFlag>();
			ttl = 0;
			reserved1 = 0;
			reserved2 = 0;
			reserved3 = 0;
		}

		if (opcode.equals(TapOpcode.MUTATION)) {
			itemflags = decodeInt(b, ITEM_FLAGS_OFFSET);
			itemexpiry = decodeInt(b, ITEM_EXPIRY_OFFSET);
			vbucketstate = 0;
			revid = new byte[engineprivate];
			System.arraycopy(b, KEY_OFFSET, revid, 0, engineprivate);
			key = new byte[keylength];
			System.arraycopy(b, KEY_OFFSET + engineprivate, key, 0, keylength);
			value = new byte[b.length - keylength - engineprivate - KEY_OFFSET];
			System.arraycopy(b, (b.length - value.length), value, 0, value.length);
		} else if (opcode.equals(TapOpcode.DELETE)) {
			itemflags = 0;
			itemexpiry = 0;
			vbucketstate = 0;
			key = new byte[keylength];
			System.arraycopy(b, ITEM_FLAGS_OFFSET, key, 0, keylength);
			value = new byte[0];
			revid = new byte[0];
		} else if (opcode.equals(TapOpcode.VBUCKETSET)) {
			itemflags = 0;
			itemexpiry = 0;
			vbucketstate = decodeInt(b, ITEM_FLAGS_OFFSET);
			key = new byte[0];
			value = new byte[0];
			revid = new byte[0];
		} else {
			itemflags = 0;
			itemexpiry = 0;
			vbucketstate = 0;
			key = new byte[0];
			value = new byte[0];
			revid = new byte[0];
		}
