		@SuppressWarnings("resource" /* java 7 */)
		TemporaryBuffer.Heap raw = new TemporaryBuffer.Heap(bufArray.length);
		InflaterInputStream inf = new InflaterInputStream(
				new ByteArrayInputStream(bufArray, ptr, bufArray.length));
		raw.copy(inf);
		inf.close();
		return raw.toByteArray();
