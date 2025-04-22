		DeltaStream ds = new DeltaStream(new InflaterInputStream(delta)
		if (size == SIZE_UNKNOWN)
			size = ds.getSize();

		final InputStream in = new BufferedInputStream(ds
		return new ObjectStream.Filter(getType()
