				if (mode == FileMode.REGULAR_FILE) {
				} else if (mode == FileMode.EXECUTABLE_FILE
						|| mode == FileMode.SYMLINK) {
					entry.setUnixMode(mode.getBits());
				} else {
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
