	public long decodeLong(byte[] b) {
		long rv=0;
		for(byte i : b) {
			rv = (rv << 8) | (i<0?256+i:i);
		}
		return rv;
	}
