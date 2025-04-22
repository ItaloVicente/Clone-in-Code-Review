	private void hashLargeObject(ObjectLoader obj) throws IOException
			TableFullException {
		ObjectStream in1 = obj.openStream();
		boolean text;
		try {
			text = !RawText.isBinary(in1);
		} finally {
			in1.close();
		}

		ObjectStream in2 = obj.openStream();
		try {
			hash(in2
		} finally {
			in2.close();
		}
	}

