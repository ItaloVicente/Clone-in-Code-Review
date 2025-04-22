		File of = null;
		FileOutputStream fos;
		if (!inCore) {
			File workTree = db.getWorkTree();
			if (workTree == null)
				throw new UnsupportedOperationException();

			of = new File(workTree
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
		}
		else if (!result.containsConflicts()) {
			of = File.createTempFile("merge_"
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
