		@SuppressWarnings("resource" /* java 7 */)
		TemporaryBuffer.Heap tmp = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
		tmp.write(out);
		tmp.write(last);
		tmp.copy(in);
		return ByteBuffer.wrap(tmp.toByteArray());
