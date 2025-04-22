	public void setAbbreviationLength(final int count) {
		if (count < 0)
			throw new IllegalArgumentException(
					JGitText.get().abbreviationLengthMustBeNonNegative);
		abbreviationLength = count;
	}

	public void setRawTextFactory(RawText.Factory type) {
		rawTextFactory = type;
	}

	public void flush() throws IOException {
		out.flush();
	}

	public void format(List<? extends DiffEntry> entries) throws IOException {
		for (DiffEntry ent : entries)
			format(ent);
	}

	public void format(DiffEntry ent) throws IOException {
		writeDiffHeader(out

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			writeGitLinkDiffText(out
		} else {
			byte[] aRaw = open(ent.getOldMode()
			byte[] bRaw = open(ent.getNewMode()

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				out.write(encodeASCII("Binary files differ\n"));

			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				formatEdits(a
			}
		}
	}

	private void writeGitLinkDiffText(OutputStream o
			throws IOException {
		if (ent.getOldMode() == GITLINK) {
			o.write(encodeASCII("-Subproject commit " + ent.getOldId().name()
					+ "\n"));
		}
		if (ent.getNewMode() == GITLINK) {
			o.write(encodeASCII("+Subproject commit " + ent.getNewId().name()
					+ "\n"));
		}
	}

	private void writeDiffHeader(OutputStream o
			throws IOException {
		String oldName = quotePath("a/" + ent.getOldName());
		String newName = quotePath("b/" + ent.getNewName());
		o.write(encode("diff --git " + oldName + " " + newName + "\n"));

		switch (ent.getChangeType()) {
		case ADD:
			o.write(encodeASCII("new file mode "));
			ent.getNewMode().copyTo(o);
			o.write('\n');
			break;

		case DELETE:
			o.write(encodeASCII("deleted file mode "));
			ent.getOldMode().copyTo(o);
			o.write('\n');
			break;

		case RENAME:
			o.write(encodeASCII("similarity index " + ent.getScore() + "%"));
			o.write('\n');

			o.write(encode("rename from " + quotePath(ent.getOldName())));
			o.write('\n');

			o.write(encode("rename to " + quotePath(ent.getNewName())));
			o.write('\n');
			break;

		case COPY:
			o.write(encodeASCII("similarity index " + ent.getScore() + "%"));
			o.write('\n');

			o.write(encode("copy from " + quotePath(ent.getOldName())));
			o.write('\n');

			o.write(encode("copy to " + quotePath(ent.getNewName())));
			o.write('\n');

			if (!ent.getOldMode().equals(ent.getNewMode())) {
				o.write(encodeASCII("new file mode "));
				ent.getNewMode().copyTo(o);
				o.write('\n');
			}
			break;
		}

		switch (ent.getChangeType()) {
		case RENAME:
		case MODIFY:
			if (!ent.getOldMode().equals(ent.getNewMode())) {
				o.write(encodeASCII("old mode "));
				ent.getOldMode().copyTo(o);
				o.write('\n');

				o.write(encodeASCII("new mode "));
				ent.getNewMode().copyTo(o);
				o.write('\n');
			}
		}

				+ format(ent.getNewId())));
		if (ent.getOldMode().equals(ent.getNewMode())) {
			o.write(' ');
			ent.getNewMode().copyTo(o);
		}
		o.write('\n');
		o.write(encode("--- " + oldName + '\n'));
		o.write(encode("+++ " + newName + '\n'));
	}

	private String format(AbbreviatedObjectId oldId) {
		if (oldId.isComplete() && db != null)
			oldId = oldId.toObjectId().abbreviate(db
		return oldId.name();
	}

	private static String quotePath(String name) {
		String q = QuotedString.GIT_PATH.quote(name);
		return ('"' + name + '"').equals(q) ? name : q;
	}

	private byte[] open(FileMode mode
			throws IOException {
		if (mode == FileMode.MISSING)
			return new byte[] {};

		if (mode.getObjectType() != Constants.OBJ_BLOB)
			return new byte[] {};

		if (db == null)
			throw new IllegalStateException(JGitText.get().repositoryIsRequired);
		if (id.isComplete()) {
			ObjectLoader ldr = db.openObject(id.toObjectId());
			return ldr.getCachedBytes();
		}

		return new byte[] {};
	}

