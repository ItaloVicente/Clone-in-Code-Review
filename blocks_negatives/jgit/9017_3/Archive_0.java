			final ZipArchiveEntry entry = new ZipArchiveEntry(name);
			final ObjectLoader loader = reader.open(idBuf);
			entry.setSize(loader.getSize());

			if (mode == FileMode.REGULAR_FILE)
			else if (mode == FileMode.EXECUTABLE_FILE ||
				 mode == FileMode.SYMLINK)
				entry.setUnixMode(mode.getBits());
			else
						CLIText.get().archiveEntryModeIgnored, //
						name));

			out.putArchiveEntry(entry);
			loader.copyTo(out);
			out.closeArchiveEntry();
