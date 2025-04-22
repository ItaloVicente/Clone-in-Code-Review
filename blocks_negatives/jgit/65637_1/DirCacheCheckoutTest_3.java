		TreeWalk walk = new TreeWalk(db);
		walk.setRecursive(false);
		walk.addTree(new FileTreeIterator(db));
		String expectedValue;
		String path;
		int nrFiles = 0;
		FileTreeIterator ft;
		while (walk.next()) {
			ft = walk.getTree(0, FileTreeIterator.class);
			path = ft.getEntryPathString();
			expectedValue = i.get(path);
			File file = new File(db.getWorkTree(), path);
			assertTrue(file.exists());
			if (file.isFile()) {
				assertNotNull("found unexpected file for path " + path
						+ " in workdir", expectedValue);
				FileInputStream is = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				int offset = 0;
				int numRead = 0;
				while (offset < buffer.length
						&& (numRead = is.read(buffer, offset, buffer.length
								- offset)) >= 0) {
					offset += numRead;
				}
				is.close();
				assertArrayEquals("unexpected content for path " + path
						+ " in workDir. ", buffer, i.get(path).getBytes());
				nrFiles++;
			} else if (file.isDirectory()) {
				if (file.list().length == 0) {
					assertEquals("found unexpected empty folder for path "
							+ path + " in workDir. ", "/", i.get(path));
