
	/**
	 * Format a Tree in canonical format.
	 *
	 * @param tree
	 *            the tree object to format
	 * @return canonical encoding of the tree object.
	 * @throws IOException
	 *             the tree cannot be loaded, or its not in a writable state.
	 */
	public final byte[] format(Tree tree) throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		for (TreeEntry e : tree.members()) {
			ObjectId id = e.getId();
			if (id == null)
				throw new ObjectWritingException(MessageFormat.format(JGitText
						.get().objectAtPathDoesNotHaveId, e.getFullName()));

			e.getMode().copyTo(o);
			o.write(' ');
			o.write(e.getNameUTF8());
			o.write(0);
			id.copyRawTo(o);
		}
		return o.toByteArray();
	}
