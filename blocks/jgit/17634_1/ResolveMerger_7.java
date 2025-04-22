		final File outputFile = File.createTempFile("merge_"
		OutputStream output = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(outputFile));
			copyTo(content
		} finally {
			if (output != null)
				output.close();
		}
		return outputFile;
	}

	private static void copyTo(CanonicalTreeParser content
			OutputStream stream) throws IOException {
		db.open(content.getEntryObjectId()).copyTo(stream);
	}

	private static boolean isIndexDirty(TreeWalk tw) {
