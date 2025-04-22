
	static private void warnArchiveEntryModeIgnored(String name) {
				CLIText.get().archiveEntryModeIgnored
				name));
	}

	public enum Format {
		ZIP
	};

	private static interface Archiver {
		ArchiveOutputStream createArchiveOutputStream(OutputStream s);
		void putEntry(String path
				ObjectLoader loader
				throws IOException;
	}

	private static final Map<Format
	static {
		Map<Format
		fmts.put(Format.ZIP
			@Override
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new ZipArchiveOutputStream(s);
			}

			@Override
			public void putEntry(String path
					ObjectLoader loader
					throws IOException {
				final ZipArchiveEntry entry = new ZipArchiveEntry(path);

				if (mode == FileMode.REGULAR_FILE)
				else if (mode == FileMode.EXECUTABLE_FILE ||
					 mode == FileMode.SYMLINK)
					entry.setUnixMode(mode.getBits());
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
