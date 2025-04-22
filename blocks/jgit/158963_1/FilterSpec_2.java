
		String[] filters = filterLine.startsWith(COMBINE)
				: new String[] { filterLine };

		long blobLimit = -1;
		long treeDepthLimit = -1;
		for (String filter : filters) {
			if (filter.equals(BLOB_NONE)) {
				blobLimit = 0;
				continue;
