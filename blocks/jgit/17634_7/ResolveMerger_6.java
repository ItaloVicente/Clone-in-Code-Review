		OutputStream outputFile = null;
		try {
			outputFile = new FileOutputStream(output);
			stream.writeTo(outputFile);
		} finally {
			if (outputFile != null)
				outputFile.close();
		}
