	public void format(final OutputStream out
			List<? extends DiffEntry> entries) throws IOException {
		for(DiffEntry ent : entries) {
			if (ent instanceof FileHeader) {
				format(
						out
						(FileHeader) ent
						newRawText(open(src
						newRawText(open(src
			} else {
				format(out
			}
		}
	}

	private void format(OutputStream out
			throws IOException {
		String oldName = quotePath("a/" + ent.getOldName());
		String newName = quotePath("b/" + ent.getNewName());
		out.write(encode("diff --git " + oldName + " " + newName + "\n"));

		switch(ent.getChangeType()) {
		case ADD:
			out.write(encodeASCII("new file mode "));
			ent.getNewMode().copyTo(out);
			out.write('\n');
			break;

		case DELETE:
			out.write(encodeASCII("deleted file mode "));
			ent.getOldMode().copyTo(out);
			out.write('\n');
			break;

		case RENAME:
			out.write(encode("similarity index " + ent.getScore() + "%"));
			out.write('\n');

			out.write(encode("rename from " + quotePath(ent.getOldName())));
			out.write('\n');

			out.write(encode("rename to " + quotePath(ent.getNewName())));
			out.write('\n');
			break;

		case COPY:
			out.write(encode("similarity index " + ent.getScore() + "%"));
			out.write('\n');

			out.write(encode("copy from " + quotePath(ent.getOldName())));
			out.write('\n');

			out.write(encode("copy to " + quotePath(ent.getNewName())));
			out.write('\n');

			if (!ent.getOldMode().equals(ent.getNewMode())) {
				out.write(encodeASCII("new file mode "));
				ent.getNewMode().copyTo(out);
				out.write('\n');
			}
			break;
		}

		switch (ent.getChangeType()) {
		case RENAME:
		case MODIFY:
			if (!ent.getOldMode().equals(ent.getNewMode())) {
				out.write(encodeASCII("old mode "));
				ent.getOldMode().copyTo(out);
				out.write('\n');

				out.write(encodeASCII("new mode "));
				ent.getNewMode().copyTo(out);
				out.write('\n');
			}
		}

				+ format(src
				+ format(src
		if (ent.getOldMode().equals(ent.getNewMode())) {
			out.write(' ');
			ent.getNewMode().copyTo(out);
		}
		out.write('\n');
		out.write(encode("--- " + oldName + '\n'));
		out.write(encode("+++ " + newName + '\n'));

		byte[] aRaw = open(src
		byte[] bRaw = open(src

		if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
			out.write(encodeASCII("Binary files differ\n"));

		} else {
			RawText a = newRawText(aRaw);
			RawText b = newRawText(bRaw);
			formatEdits(out
		}
	}

	protected RawText newRawText(byte[] content) {
		return new RawText(content);
	}

	private String format(Repository db
		if (oldId.isComplete())
			oldId = oldId.toObjectId().abbreviate(db
		return oldId.name();
	}

	private static String quotePath(String name) {
		String q = QuotedString.GIT_PATH.quote(name);
		return ('"' + name + '"').equals(q) ? name : q;
	}

	private byte[] open(Repository src
			throws IOException {
		if (mode == FileMode.MISSING)
			return new byte[] {};

		if (mode.getObjectType() != Constants.OBJ_BLOB)
			return new byte[] {};

		if (id.isComplete()) {
			ObjectLoader ldr = src.openObject(id.toObjectId());
			return ldr.getCachedBytes();
		}

		return new byte[] {};
	}

