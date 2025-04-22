	public static byte[] concat(byte[]... b) {
		int n = 0;
		for (byte[] a : b) {
			n += a.length;
		}

		byte[] data = new byte[n];
		n = 0;
		for (byte[] a : b) {
			System.arraycopy(a
			n += a.length;
		}
		return data;
	}
