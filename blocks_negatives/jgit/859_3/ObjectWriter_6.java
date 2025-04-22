	public ObjectId writeTag(final Tag c) throws IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final OutputStreamWriter w = new OutputStreamWriter(os,
				Constants.CHARSET);

		w.write("object ");
		c.getObjId().copyTo(w);
		w.write('\n');

		w.write("type ");
		w.write(c.getType());
		w.write("\n");

		w.write("tag ");
		w.write(c.getTag());
		w.write("\n");

		w.write("tagger ");
		w.write(c.getAuthor().toExternalString());
		w.write('\n');

		w.write('\n');
		w.write(c.getMessage());
		w.close();

		return writeTag(os.toByteArray());
	}

	private ObjectId writeCommit(final byte[] b) throws IOException {
		return writeCommit(b.length, new ByteArrayInputStream(b));
	}

	private ObjectId writeCommit(final long len, final InputStream is)
			throws IOException {
		return writeObject(Constants.OBJ_COMMIT, len, is, true);
	}

	private ObjectId writeTag(final long len, final InputStream is)
		throws IOException {
		return writeObject(Constants.OBJ_TAG, len, is, true);
