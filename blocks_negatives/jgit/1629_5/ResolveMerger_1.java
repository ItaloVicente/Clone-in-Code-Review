		File workTree = db.getWorkTree();
		if (workTree == null)
			throw new UnsupportedOperationException();

		File of = new File(workTree, tw.getPathString());
		FileOutputStream fos = new FileOutputStream(of);
		try {
			fmt.formatMerge(fos, result, Arrays.asList(commitNames),
					Constants.CHARACTER_ENCODING);
		} finally {
			fos.close();
