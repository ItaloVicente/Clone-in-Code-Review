			entry.setSize(loader.getSize());

			if (mode == FileMode.REGULAR_FILE)
			else if (mode == FileMode.EXECUTABLE_FILE ||
				 mode == FileMode.SYMLINK)
				entry.setUnixMode(mode.getBits());
			else
						CLIText.get().archiveEntryModeIgnored, //
						name));
