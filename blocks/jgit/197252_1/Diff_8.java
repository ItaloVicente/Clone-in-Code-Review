
	static void nameOnly(ThrowingPrintWriter out
			throws IOException {
		for (DiffEntry ent : files) {
			switch (ent.getChangeType()) {
				case ADD:
					out.println(ent.getNewPath());
					break;
				case DELETE:
					out.println(ent.getOldPath());
					break;
				case MODIFY:
					out.println(ent.getNewPath());
					break;
				case COPY:
					out.println(ent.getNewPath());
					break;
				case RENAME:
					out.println(ent.getNewPath());
					break;
			}
		}
	}
