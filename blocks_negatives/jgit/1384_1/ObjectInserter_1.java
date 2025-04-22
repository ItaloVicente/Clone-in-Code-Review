
	/**
	 * Format a Commit in canonical format.
	 *
	 * @param commit
	 *            the commit object to format
	 * @return canonical encoding of the commit object.
	 * @throws UnsupportedEncodingException
	 *             the commit's chosen encoding isn't supported on this JVM.
	 */
	public final byte[] format(CommitBuilder commit)
			throws UnsupportedEncodingException {
		Charset encoding = commit.getEncoding();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os, encoding);
		try {
			os.write(htree);
			os.write(' ');
			commit.getTreeId().copyTo(os);
			os.write('\n');

			for (ObjectId p : commit.getParentIds()) {
				os.write(hparent);
				os.write(' ');
				p.copyTo(os);
				os.write('\n');
			}

			os.write(hauthor);
			os.write(' ');
			w.write(commit.getAuthor().toExternalString());
			w.flush();
			os.write('\n');

			os.write(hcommitter);
			os.write(' ');
			w.write(commit.getCommitter().toExternalString());
			w.flush();
			os.write('\n');

			if (encoding != Constants.CHARSET) {
				os.write(hencoding);
				os.write(' ');
				os.write(Constants.encodeASCII(encoding.name()));
				os.write('\n');
			}

			os.write('\n');

			if (commit.getMessage() != null) {
				w.write(commit.getMessage());
				w.flush();
			}
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
		return os.toByteArray();
	}

	/**
	 * Format a Tag in canonical format.
	 *
	 * @param tag
	 *            the tag object to format
	 * @return canonical encoding of the tag object.
	 */
	public final byte[] format(TagBuilder tag) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os, Constants.CHARSET);
		try {
			w.write("object ");
			tag.getObjectId().copyTo(w);
			w.write('\n');

			w.write("type ");
			w.write(Constants.typeString(tag.getObjectType()));
			w.write("\n");

			w.write("tag ");
			w.write(tag.getTag());
			w.write("\n");

			if (tag.getTagger() != null) {
				w.write("tagger ");
				w.write(tag.getTagger().toExternalString());
				w.write('\n');
			}

			w.write('\n');
			if (tag.getMessage() != null)
				w.write(tag.getMessage());
			w.close();
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
		return os.toByteArray();
	}
