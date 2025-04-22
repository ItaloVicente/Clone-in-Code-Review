	public byte decodeByte(byte[] in) {
		assert in.length <= 1 : "Too long for a byte";
		byte rv=0;
		if(in.length == 1) {
			rv=in[0];
		}
		return rv;
	}
