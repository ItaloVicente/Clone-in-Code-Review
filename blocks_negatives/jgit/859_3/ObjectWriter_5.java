
		os.write('\n');
		w.write(c.getMessage());
		w.flush();

		return writeCommit(os.toByteArray());
	}

	private ObjectId writeTag(final byte[] b) throws IOException {
		return writeTag(b.length, new ByteArrayInputStream(b));
