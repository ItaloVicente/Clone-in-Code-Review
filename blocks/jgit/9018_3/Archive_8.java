		fmts.put(Format.TAR
			@Override
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new TarArchiveOutputStream(s);
			}

			@Override
			public void putEntry(String path
					ObjectLoader loader
					throws IOException {
				if (mode == FileMode.SYMLINK) {
							path
						loader.getCachedBytes(100)
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
