		if (mode == FileMode.TREE) {
			out.putArchiveEntry(entry);
			out.closeArchiveEntry();
			return;
		}

		if (mode == FileMode.REGULAR_FILE) {
		} else if (mode == FileMode.EXECUTABLE_FILE) {
