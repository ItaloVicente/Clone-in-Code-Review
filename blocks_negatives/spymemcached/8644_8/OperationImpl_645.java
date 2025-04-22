	/**
	 * Set some arguments for an operation into the given byte buffer.
	 */
	protected final void setArguments(ByteBuffer bb, Object... args) {
		boolean wasFirst=true;
		for(Object o : args) {
			if(wasFirst) {
				wasFirst=false;
			} else {
				bb.put((byte)' ');
			}
			bb.put(KeyUtil.getKeyBytes(String.valueOf(o)));
		}
		bb.put(CRLF);
	}
