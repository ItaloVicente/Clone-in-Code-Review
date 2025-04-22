	public ObjectId writeCommit(final Commit c) throws IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		String encoding = c.getEncoding();
		if (encoding == null)
			encoding = Constants.CHARACTER_ENCODING;
		final OutputStreamWriter w = new OutputStreamWriter(os, encoding);

		os.write(htree);
		os.write(' ');
		c.getTreeId().copyTo(os);
		os.write('\n');

		ObjectId[] ps = c.getParentIds();
		for (int i=0; i<ps.length; ++i) {
			os.write(hparent);
			os.write(' ');
			ps[i].copyTo(os);
			os.write('\n');
		}

		os.write(hauthor);
		os.write(' ');
		w.write(c.getAuthor().toExternalString());
		w.flush();
		os.write('\n');

		os.write(hcommitter);
		os.write(' ');
		w.write(c.getCommitter().toExternalString());
		w.flush();
		os.write('\n');

		if (!encoding.equals(Constants.CHARACTER_ENCODING)) {
			os.write(hencoding);
			os.write(' ');
			os.write(Constants.encodeASCII(encoding));
			os.write('\n');
