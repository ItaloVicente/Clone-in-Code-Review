
	static void nameOnly(ThrowingPrintWriter out
			throws IOException {
		for (DiffEntry ent : files) {
			switch (ent.getChangeType()) {
				case ADD:
					break;
				case DELETE:
					break;
				case MODIFY:
					break;
				case COPY:
					break;
				case RENAME:
					break;
			}
		}
	}
