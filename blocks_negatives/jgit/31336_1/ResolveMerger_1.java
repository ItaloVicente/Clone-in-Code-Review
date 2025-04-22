		MergeFormatter fmt = new MergeFormatter();
		File of = null;
		FileOutputStream fos;
		if (!inCore) {
			File workTree = db.getWorkTree();
			if (workTree == null)
				throw new UnsupportedOperationException();

			FS fs = db.getFS();
			of = new File(workTree, tw.getPathString());
			File parentFolder = of.getParentFile();
			if (!fs.exists(parentFolder))
				parentFolder.mkdirs();
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos, result, Arrays.asList(commitNames),
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
		} else if (!result.containsConflicts()) {
			of = File.createTempFile("merge_", "_temp", null); //$NON-NLS-1$ //$NON-NLS-2$
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos, result, Arrays.asList(commitNames),
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
