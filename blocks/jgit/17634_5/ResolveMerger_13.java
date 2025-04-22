		final File outputFile = File.createTempFile("merge_"
		OutputStream output = null;
		try {
			output = new FileOutputStream(outputFile);
			db.open(content.getEntryObjectId()).copyTo(output);
		} finally {
			if (output != null)
				output.close();
		}
		return outputFile;
	}
