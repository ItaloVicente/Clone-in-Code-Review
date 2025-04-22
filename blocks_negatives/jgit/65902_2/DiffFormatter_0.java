	/**
	 * @param o
	 *            the stream the formatter will write line data to
	 * @param ent
	 *            the DiffEntry to create the FileHeader for
	 * @throws IOException
	 *             writing to the supplied stream failed.
	 */
	protected void formatIndexLine(OutputStream o, DiffEntry ent)
			throws IOException {
		formatIndexLine(o, ent, false);
	}

