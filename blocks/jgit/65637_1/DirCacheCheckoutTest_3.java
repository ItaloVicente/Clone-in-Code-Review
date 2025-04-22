		try (TreeWalk walk = new TreeWalk(db)) {
			walk.setRecursive(false);
			walk.addTree(new FileTreeIterator(db));
			String expectedValue;
			String path;
			int nrFiles = 0;
			FileTreeIterator ft;
			while (walk.next()) {
				ft = walk.getTree(0
				path = ft.getEntryPathString();
				expectedValue = i.get(path);
				File file = new File(db.getWorkTree()
				assertTrue(file.exists());
				if (file.isFile()) {
					assertNotNull("found unexpected file for path " + path
							+ " in workdir"
					FileInputStream is = new FileInputStream(file);
					byte[] buffer = new byte[(int) file.length()];
					int offset = 0;
					int numRead = 0;
					while (offset < buffer.length
							&& (numRead = is.read(buffer
									- offset)) >= 0) {
						offset += numRead;
					}
					is.close();
					assertArrayEquals("unexpected content for path " + path
							+ " in workDir. "
