
	public byte[] getRevID() {
		return revid;
	}

	public ByteBuffer getBytes() {
		ByteBuffer bb = ByteBuffer.allocate(HEADER_LENGTH + getTotalbody());
		bb.put(magic.magic);
		bb.put(opcode.opcode);
		bb.putShort(keylength);
		bb.put(extralength);
		bb.put(datatype);
		bb.putShort(vbucket);
		bb.putInt(totalbody);
		bb.putInt(opaque);
		bb.putLong(cas);
		
		if (opcode.equals(TapOpcode.NOOP)) {
			return bb;
		}

		bb.putShort(engineprivate);
		
		short flag = 0;
		for (int i = 0; i < flags.size(); i++) {
			flag |= flags.get(i).flag;
		}
		bb.putShort(flag);
		bb.put(ttl);
		bb.put(reserved1);
		bb.put(reserved2);
		bb.put(reserved3);

		if (opcode.equals(TapOpcode.MUTATION)) {
			bb.putInt(itemflags);
			bb.putInt(itemexpiry);
			bb.put(revid);
			bb.put(key);
			bb.put(value);
		} else if (opcode.equals(TapOpcode.DELETE)) {
			bb.put(key);
		} else if (opcode.equals(TapOpcode.VBUCKETSET)) {
			bb.putInt(vbucketstate);
		}
		return bb;
	}
