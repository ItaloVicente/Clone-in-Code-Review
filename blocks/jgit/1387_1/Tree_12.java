	public byte[] format() throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		for (TreeEntry e : members()) {
			ObjectId id = e.getId();
			if (id == null)
				throw new ObjectWritingException(MessageFormat.format(JGitText
						.get().objectAtPathDoesNotHaveId

			e.getMode().copyTo(o);
			o.write(' ');
			o.write(e.getNameUTF8());
			o.write(0);
			id.copyRawTo(o);
		}
		return o.toByteArray();
	}

