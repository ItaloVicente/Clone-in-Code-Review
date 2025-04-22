		if (FileMode.TREE.equals(mode)) {
			out.putArchiveEntry(entry);
			out.closeArchiveEntry();
			return;
		}

		if (FileMode.REGULAR_FILE.equals(mode)) {
		} else if (FileMode.EXECUTABLE_FILE.equals(mode)) {
