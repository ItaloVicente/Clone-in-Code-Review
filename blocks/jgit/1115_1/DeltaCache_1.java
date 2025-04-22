	byte[] resize(byte[] data
		if (data.length != actLen) {
			byte[] nbuf = new byte[actLen];
			System.arraycopy(data
			data = nbuf;
		}
		return data;
	}

