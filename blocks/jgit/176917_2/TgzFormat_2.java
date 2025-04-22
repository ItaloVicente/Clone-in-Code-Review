		GzipCompressorOutputStream out;
		int compressionLevel = getCompressionLevel(o);
		if (compressionLevel != -1) {
			GzipParameters parameters = new GzipParameters();
			parameters.setCompressionLevel(compressionLevel);
			out = new GzipCompressorOutputStream(s
		} else {
			out = new GzipCompressorOutputStream(s);
		}
