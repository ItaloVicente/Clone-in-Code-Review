	private Deflater deflater() {
		if (myDeflater == null)
			myDeflater = new Deflater(compressionLevel);
		return myDeflater;
	}

