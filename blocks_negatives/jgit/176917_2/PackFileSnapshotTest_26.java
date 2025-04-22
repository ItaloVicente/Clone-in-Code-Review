		PackFile pf = repackAndCheck(5, null, null, null);
		Path packFilePath = pf.getPackFile().toPath();
		Path packFileBasePath = packFilePath.resolveSibling(
				packFilePath.getFileName().toString().replaceAll(".pack", ""));
		AnyObjectId chk1 = pf.getPackChecksum();
		String name = pf.getPackName();
		Long length = Long.valueOf(pf.getPackFile().length());
