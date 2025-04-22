	private void writeDeltaObjectDeflate(PackOutputStream out
			final ObjectToPack otp) throws IOException {
		TemporaryBuffer.Heap delta = delta(otp);
		out.writeHeader(otp

		Deflater deflater = deflater();
		deflater.reset();
		DeflaterOutputStream dst = new DeflaterOutputStream(out
		delta.writeTo(dst
		dst.finish();
	}

	private TemporaryBuffer.Heap delta(final ObjectToPack otp)
			throws IOException {
		DeltaIndex index = new DeltaIndex(buffer(reader
		byte[] res = buffer(reader

		TemporaryBuffer.Heap delta = new TemporaryBuffer.Heap(res.length);
		index.encode(delta
		return delta;
	}

	byte[] buffer(ObjectReader or
		ObjectLoader ldr = or.open(objId);
		if (!ldr.isLarge())
			return ldr.getCachedBytes();


		long sz = ldr.getSize();
		if (getBigFileThreshold() <= sz || Integer.MAX_VALUE < sz)
			throw new LargeObjectException(objId.copy());

		byte[] buf;
		try {
			buf = new byte[(int) sz];
		} catch (OutOfMemoryError noMemory) {
			LargeObjectException e;

			e = new LargeObjectException(objId.copy());
			e.initCause(noMemory);
			throw e;
		}
		InputStream in = ldr.openStream();
		try {
			IO.readFully(in
		} finally {
			in.close();
		}
		return buf;
	}

