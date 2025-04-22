	@Override
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

		if (hasFlags) {
			int flag = 0;
			for (int i = 0; i < flagList.size(); i++) {
				flag |= flagList.get(i).flag;
