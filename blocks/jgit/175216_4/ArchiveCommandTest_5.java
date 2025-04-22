	private static ArchiveInputStream createArchiveInputStream (String fmt
			throws IOException {
		switch (fmt) {
			case "tar":
				return new TarArchiveInputStream(bi);
			case "tgz":
				return new TarArchiveInputStream(new GzipCompressorInputStream(bi));
			case "tbz2":
				return new TarArchiveInputStream(new BZip2CompressorInputStream(bi));
			case "txz":
				return new TarArchiveInputStream(new XZCompressorInputStream(bi));
			case "zip":
				return new ZipArchiveInputStream(new BufferedInputStream(bi));
		}
		throw new IllegalArgumentException("Format " + fmt + " is not supported.");
	}

