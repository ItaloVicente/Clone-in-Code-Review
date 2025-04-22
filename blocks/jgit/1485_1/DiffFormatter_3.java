	private void formatHeader(ByteArrayOutputStream o
			throws IOException {
		final ChangeType type = ent.getChangeType();
		final String oldp = ent.getOldPath();
		final String newp = ent.getNewPath();
		final FileMode oldMode = ent.getOldMode();
		final FileMode newMode = ent.getNewMode();

		o.write(encodeASCII("diff --git "));
		o.write(encode(quotePath(oldPrefix + (type == ADD ? newp : oldp))));
		o.write(' ');
		o.write(encode(quotePath(newPrefix + (type == DELETE ? oldp : newp))));
		o.write('\n');

		switch (type) {
		case ADD:
			o.write(encodeASCII("new file mode "));
			newMode.copyTo(o);
			o.write('\n');
			break;

		case DELETE:
			o.write(encodeASCII("deleted file mode "));
			oldMode.copyTo(o);
			o.write('\n');
			break;

		case RENAME:
			o.write(encodeASCII("similarity index " + ent.getScore() + "%"));
			o.write('\n');

			o.write(encode("rename from " + quotePath(oldp)));
			o.write('\n');

			o.write(encode("rename to " + quotePath(newp)));
			o.write('\n');
			break;

		case COPY:
			o.write(encodeASCII("similarity index " + ent.getScore() + "%"));
			o.write('\n');

			o.write(encode("copy from " + quotePath(oldp)));
			o.write('\n');

			o.write(encode("copy to " + quotePath(newp)));
			o.write('\n');

			if (!oldMode.equals(newMode)) {
				o.write(encodeASCII("new file mode "));
				newMode.copyTo(o);
				o.write('\n');
			}
			break;

		case MODIFY:
			if (0 < ent.getScore()) {
				o.write(encodeASCII("dissimilarity index "
						+ (100 - ent.getScore()) + "%"));
				o.write('\n');
			}
			break;
		}

		if ((type == MODIFY || type == RENAME) && !oldMode.equals(newMode)) {
			o.write(encodeASCII("old mode "));
			oldMode.copyTo(o);
			o.write('\n');

			o.write(encodeASCII("new mode "));
			newMode.copyTo(o);
			o.write('\n');
		}

		if (!ent.getOldId().equals(ent.getNewId())) {
					+ format(ent.getNewId())));
			if (oldMode.equals(newMode)) {
				o.write(' ');
				newMode.copyTo(o);
			}
			o.write('\n');
		}
	}

	private void formatOldNewPaths(ByteArrayOutputStream o
			throws IOException {
		final String oldp;
		final String newp;

		switch (ent.getChangeType()) {
		case ADD:
			oldp = DiffEntry.DEV_NULL;
			newp = quotePath(newPrefix + ent.getNewPath());
			break;

		case DELETE:
			oldp = quotePath(oldPrefix + ent.getOldPath());
			newp = DiffEntry.DEV_NULL;
			break;

		default:
			oldp = quotePath(oldPrefix + ent.getOldPath());
			newp = quotePath(newPrefix + ent.getNewPath());
			break;
		}

		o.write(encode("--- " + oldp + "\n"));
		o.write(encode("+++ " + newp + "\n"));
	}

