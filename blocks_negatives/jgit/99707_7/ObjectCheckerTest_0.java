		byte[] data = new byte[n];
		n = 0;
		for (byte[] a : b) {
			System.arraycopy(a, 0, data, n, a.length);
			n += a.length;
		}
		return data;
	}
