		} else {
			if (mode == FileMode.SYMLINK.getBits()) {
				return !new File(readSymlinkTarget(current())).equals(
						new File(readContentAsNormalizedString(entry, reader)));
			}
			return true;
