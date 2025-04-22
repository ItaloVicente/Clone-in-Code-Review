
	static private void warnArchiveEntryModeIgnored(String name) {
				CLIText.get().archiveEntryModeIgnored
				name));
	}

	public enum Format {
		ZIP
	};
	private static interface Archiver {
		ArchiveOutputStream createArchiveOutputStream(OutputStream s);
		ArchiveEntry createArchiveEntry(String path
	}
	private static final Map<Format
	static {
		Map<Format
		fmts.put(Format.ZIP
			@Override
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				ArchiveStreamFactory factory = new ArchiveStreamFactory();
				try {
							ArchiveStreamFactory.ZIP
				} catch (ArchiveException e) {
					throw die("this can't happen: missing zip support");
				}
			}

			@Override
			public ArchiveEntry createArchiveEntry(String path
				final ZipArchiveEntry entry = new ZipArchiveEntry(path);

				if (mode == FileMode.REGULAR_FILE)
				else if (mode == FileMode.EXECUTABLE_FILE ||
					 mode == FileMode.SYMLINK)
					entry.setUnixMode(mode.getBits());
				else
					warnArchiveEntryModeIgnored(path);
				entry.setSize(size);
				return entry;
			}
		});
		formats = fmts;
	}
