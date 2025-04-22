	    for (File content : contents) {
		if (!content.isDirectory()) {
		    continue;
		}
		if (content.getName().equals(METADATA_FOLDER)) {
		    continue;
