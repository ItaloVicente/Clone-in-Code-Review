	static {
		Map<Format, Archiver> fmts = new EnumMap<Format, Archiver>(Format.class);
		fmts.put(Format.ZIP, new Archiver() {
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new ZipArchiveOutputStream(s);
			}
