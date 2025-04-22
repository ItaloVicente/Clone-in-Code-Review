				final TarArchiveEntry entry = new TarArchiveEntry(path);
				if (mode == FileMode.REGULAR_FILE ||
				    mode == FileMode.EXECUTABLE_FILE) {
					entry.setMode(mode.getBits());
				} else {
				}
				entry.setSize(loader.getSize());
				out.putArchiveEntry(entry);
				loader.copyTo(out);
				out.closeArchiveEntry();
			}
		});
		formats = fmts;
