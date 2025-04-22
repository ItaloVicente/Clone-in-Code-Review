	private void writeDiffHeader(OutputStream o, DiffEntry ent)
			throws IOException {
		String oldName = quotePath("a/" + ent.getOldPath());
		String newName = quotePath("b/" + ent.getNewPath());
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

			o.write(encode("rename from " + quotePath(ent.getOldPath())));
			o.write('\n');

			o.write(encode("rename to " + quotePath(ent.getNewPath())));
			o.write('\n');
			break;

		case COPY:
			o.write(encodeASCII("similarity index " + ent.getScore() + "%"));
			o.write('\n');

			o.write(encode("copy from " + quotePath(ent.getOldPath())));
			o.write('\n');

			o.write(encode("copy to " + quotePath(ent.getNewPath())));
			o.write('\n');

			if (!ent.getOldMode().equals(ent.getNewMode())) {
				o.write(encodeASCII("new file mode "));
				ent.getNewMode().copyTo(o);
				o.write('\n');
			}
			break;
		case MODIFY:
			int score = ent.getScore();
			if (0 < score && score <= 100) {
				o.write(encodeASCII("dissimilarity index " + (100 - score)
						+ "%"));
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

