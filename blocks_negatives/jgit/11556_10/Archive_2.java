
	static private void warnArchiveEntryModeIgnored(String name) {
				CLIText.get().archiveEntryModeIgnored, //
				name));
	}

	public enum Format {
		ZIP,
		TAR
	}

	private static interface Archiver {
		ArchiveOutputStream createArchiveOutputStream(OutputStream s);
		void putEntry(String path, FileMode mode, //
				ObjectLoader loader, ArchiveOutputStream out) //
				throws IOException;
	}

	private static final Map<Format, Archiver> formats;
	static {
		Map<Format, Archiver> fmts = new EnumMap<Format, Archiver>(Format.class);
		fmts.put(Format.ZIP, new Archiver() {
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new ZipArchiveOutputStream(s);
			}

			public void putEntry(String path, FileMode mode, //
					ObjectLoader loader, ArchiveOutputStream out) //
					throws IOException {
				final ZipArchiveEntry entry = new ZipArchiveEntry(path);

				if (mode == FileMode.REGULAR_FILE) {
				} else if (mode == FileMode.EXECUTABLE_FILE
						|| mode == FileMode.SYMLINK) {
					entry.setUnixMode(mode.getBits());
				} else {
					warnArchiveEntryModeIgnored(path);
				}
				entry.setSize(loader.getSize());
				out.putArchiveEntry(entry);
				loader.copyTo(out);
				out.closeArchiveEntry();
			}
		});
		fmts.put(Format.TAR, new Archiver() {
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new TarArchiveOutputStream(s);
			}

			public void putEntry(String path, FileMode mode, //
					ObjectLoader loader, ArchiveOutputStream out) //
					throws IOException {
				if (mode == FileMode.SYMLINK) {
							path, TarConstants.LF_SYMLINK);
							loader.getCachedBytes(100), "UTF-8")); //$NON-NLS-1$
					out.putArchiveEntry(entry);
					out.closeArchiveEntry();
					return;
				}

				final TarArchiveEntry entry = new TarArchiveEntry(path);
				if (mode == FileMode.REGULAR_FILE ||
				    mode == FileMode.EXECUTABLE_FILE)
					entry.setMode(mode.getBits());
				else
					warnArchiveEntryModeIgnored(path);
				entry.setSize(loader.getSize());
				out.putArchiveEntry(entry);
				loader.copyTo(out);
				out.closeArchiveEntry();
			}
		});
		formats = fmts;
	}
