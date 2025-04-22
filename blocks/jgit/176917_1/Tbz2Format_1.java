		BZip2CompressorOutputStream out;
		int compressionLevel = getCompressionLevel(o);
		if (compressionLevel != -1) {
			out = new BZip2CompressorOutputStream(s
		} else {
			out = new BZip2CompressorOutputStream(s);
		}
