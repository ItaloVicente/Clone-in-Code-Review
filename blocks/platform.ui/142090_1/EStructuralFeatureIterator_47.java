	private int getLength() {
		int length;
		try {
			length = propertyNodePointer.getLength(); // TBD: cache length
		}
		catch (Throwable t) {
			length = 0;
		}
		return length;
	}
