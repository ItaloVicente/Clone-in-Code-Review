		return new PackBitmapIndexV1(fd
	}

	public static PackBitmapIndex read(InputStream fd
			SupplierWithIOException<PackIndex> packIndexSupplier
			SupplierWithIOException<PackReverseIndex> reverseIndexSupplier)
			throws IOException {
		return new PackBitmapIndexV1(fd
				reverseIndexSupplier);
