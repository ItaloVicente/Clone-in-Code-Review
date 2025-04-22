
	static void raw(ThrowingPrintWriter out
					boolean noAbbrev)
			throws IOException {
		int abbrLength = 9;
		if (noAbbrev) {
			abbrLength = 40;
		}
		for (DiffEntry ent : files) {
			out.print(String.format("%06d"
			out.print(String.format("%06d"
			out.print(ent.getOldId().name().substring(0
			out.print(ent.getNewId().name().substring(0
			switch (ent.getChangeType()) {
				case ADD:
					break;
				case DELETE:
					break;
				case MODIFY:
					break;
				case COPY:
					out.format("C%1$03d\t%2$s\t%3$s"
							ent.getOldPath()
					out.println();
					break;
				case RENAME:
					out.format("R%1$03d\t%2$s\t%3$s"
							ent.getOldPath()
					out.println();
					break;
			}
		}
	}
