		return new PackBitmapIndexV1(fd, () -> packIndex, () -> reverseIndex);
	}

	/**
	 * Read an existing pack bitmap index file from a buffered stream.
	 * <p>
	 * The format of the file will be automatically detected and a proper access
	 * implementation for that format will be constructed and returned to the
	 * caller. The file may or may not be held open by the returned instance.
	 *
	 * @param fd
	 *            stream to read the bitmap index file from. The stream must be
	 *            buffered as some small IOs are performed against the stream.
	 *            The caller is responsible for closing the stream.
	 * @param packIndexSupplier
	 *            the supplier for pack index for the corresponding pack file.
	 * @param reverseIndexSupplier
	 *            the supplier for pack reverse index for the corresponding pack
	 *            file.
	 * @return a copy of the index in-memory.
	 * @throws java.io.IOException
	 *             the stream cannot be read.
	 * @throws CorruptObjectException
	 *             the stream does not contain a valid pack bitmap index.
	 */
	public static PackBitmapIndex read(InputStream fd,
			SupplierWithIOException<PackIndex> packIndexSupplier,
			SupplierWithIOException<PackReverseIndex> reverseIndexSupplier)
			throws IOException {
		return new PackBitmapIndexV1(fd, packIndexSupplier,
				reverseIndexSupplier);
