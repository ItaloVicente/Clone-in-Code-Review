
		switch (trustPackedRefsStat) {
			case NEVER:
				break;
			case AFTER_OPEN:
				try (InputStream stream = Files.newInputStream(packedRefsFile.toPath())) {}
			case ALWAYS:
				if (!curList.snapshot.isModified(packedRefsFile)) {
					return curList;
				}
				break;
			case UNSET:
				if (trustFolderStat && !curList.snapshot.isModified(packedRefsFile)) {
					return curList;
				}
				break;
