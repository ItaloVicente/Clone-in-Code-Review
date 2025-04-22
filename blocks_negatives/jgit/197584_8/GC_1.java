		String latestId = null;
		for (String n : fileNames) {
			PackFile pf = new PackFile(packDir.toFile(), n);
			PackExt ext = pf.getPackExt();
			if (ext.equals(PACK) || ext.equals(KEEP)) {
				latestId = pf.getId();
			}
			if (latestId == null || !pf.getId().equals(latestId)) {
