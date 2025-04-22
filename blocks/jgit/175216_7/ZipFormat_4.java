		ZipArchiveOutputStream out = new ZipArchiveOutputStream(s);
		int compressionLevel = getCompressionLevel(o);
		if (compressionLevel != -1) {
			out.setLevel(compressionLevel);
		}
		return applyFormatOptions(out
