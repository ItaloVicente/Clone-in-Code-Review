		Pack p = repackAndCheck(5
		Path packFilePath = p.getPackFile().toPath();
		Path fn = packFilePath.getFileName();
		assertNotNull(fn);
		String packFileName = fn.toString();
		Path packFileBasePath = packFilePath
				.resolveSibling(packFileName.replaceAll(".pack"
		AnyObjectId chk1 = p.getPackChecksum();
		String name = p.getPackName();
		Long length = Long.valueOf(p.getPackFile().length());
