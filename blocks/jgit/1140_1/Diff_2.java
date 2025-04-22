		List<DiffEntry> files = scan();

		if (showNameAndStatusOnly) {
			nameStatus(out
			out.flush();

		} else {
			diffFmt.setRepository(db);
			diffFmt.format(files);
			diffFmt.flush();
		}
	}

	static void nameStatus(PrintWriter out
		for (DiffEntry ent : files) {
			switch (ent.getChangeType()) {
			case ADD:
				out.println("A\t" + ent.getNewName());
				break;
			case DELETE:
				out.println("D\t" + ent.getOldName());
				break;
			case MODIFY:
				out.println("M\t" + ent.getNewName());
				break;
			case COPY:
				out.format("C%1$03d\t%2$s\t%3$s"
						ent.getOldName()
				out.println();
				break;
			case RENAME:
				out.format("R%1$03d\t%2$s\t%3$s"
						ent.getOldName()
				out.println();
				break;
			}
		}
	}

	private List<DiffEntry> scan() throws IOException {
