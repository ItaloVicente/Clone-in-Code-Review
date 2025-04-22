		File workTree = db.getWorkTree();
		if (workTree == null)
			throw new UnsupportedOperationException();

		FS fs = db.getFS();
		File of = new File(workTree
		File parentFolder = of.getParentFile();
		if (!fs.exists(parentFolder))
			parentFolder.mkdirs();
		FileOutputStream fos = new FileOutputStream(of);
		try {
			new MergeFormatter().formatMerge(fos
					Arrays.asList(commitNames)
		} finally {
			fos.close();
