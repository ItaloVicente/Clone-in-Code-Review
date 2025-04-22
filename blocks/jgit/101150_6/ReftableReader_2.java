	public static ReftableReader emptyTable() {
		try {
			int len = FILE_HEADER_LEN + FILE_FOOTER_LEN;
			ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
			new ReftableWriter().begin(buf).finish();
			return new ReftableReader(BlockSource.from(buf.toByteArray()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

