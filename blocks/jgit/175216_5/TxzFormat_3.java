		XZCompressorOutputStream out;
		int compressionLevel = getCompressionLevel(o);
		if (compressionLevel != -1) {
			out = new XZCompressorOutputStream(s
		} else {
			out = new XZCompressorOutputStream(s);
		}
