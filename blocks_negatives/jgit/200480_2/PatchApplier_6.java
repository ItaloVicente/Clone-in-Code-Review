		case LITERAL_DEFLATED: {
			checkOid(fh.getOldId().toObjectId(), id, fh.getChangeType(), f,
					path);
			StreamSupplier supp = () -> new InflaterInputStream(
					new BinaryHunkInputStream(new ByteArrayInputStream(
							hunk.getBuffer(), start, length)));
			return new ContentStreamLoader(supp, hunk.getSize());
		}
		case DELTA_DEFLATED: {
			byte[] base;
			try (InputStream in = inputSupplier.load()) {
				base = IO.readWholeStream(in, 0).array();
