		StyleRange[] ranges = getStyleRanges("file", "file");
		boolean withFolder = false;
		for (StyleRange range : ranges) {
			if (range.length > 3 + project.getName().length()) {
				withFolder = true;
			}
		}
