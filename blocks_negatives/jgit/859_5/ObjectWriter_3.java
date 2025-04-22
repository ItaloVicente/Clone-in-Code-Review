	public ObjectId writeTree(final Tree t) throws IOException {
		final ByteArrayOutputStream o = new ByteArrayOutputStream();
		final TreeEntry[] items = t.members();
		for (int k = 0; k < items.length; k++) {
			final TreeEntry e = items[k];
			final ObjectId id = e.getId();

			if (id == null)
				throw new ObjectWritingException(MessageFormat.format(
						JGitText.get().objectAtPathDoesNotHaveId, e.getFullName()));

			e.getMode().copyTo(o);
			o.write(' ');
			o.write(e.getNameUTF8());
			o.write(0);
			id.copyRawTo(o);
