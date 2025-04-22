		byte[] dstbuf;
		try {
			dstbuf = new byte[sz];
		} catch (OutOfMemoryError noMemory) {
			return null;
		}

