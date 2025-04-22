
		public void assertIndex(HashMap<String
				throws CorruptObjectException
			String expectedValue;
			String path;
			assertEquals("Index has not the right size."
					theIndex.getMembers().length);
			for (int j = 0; j < theIndex.getMembers().length; j++) {
				path = theIndex.getMembers()[j].getName();
				expectedValue = i.get(path);
				assertNotNull("found unexpected entry for path "
						+ path + " in index"
				assertTrue("unexpected content for path " + path
						+ " in index. Expected: <" + expectedValue + ">"
						Arrays.equals(
								db.openBlob(
										theIndex.getMembers()[j].getObjectId())
										.getBytes()
			}
		}
	}

	public void assertWorkDir(HashMap<String
			throws CorruptObjectException
		TreeWalk walk = new TreeWalk(db);
		walk.reset();
		walk.setRecursive(true);
		walk.addTree(new FileTreeIterator(db.getWorkDir()
		String expectedValue;
		String path;
		int nrFiles = 0;
		FileTreeIterator ft;
		while (walk.next()) {
			ft = walk.getTree(0
			path = ft.getEntryPathString();
			expectedValue = i.get(path);
			assertNotNull("found unexpected file for path "
					+ path + " in workdir"
			File file = new File(db.getWorkDir()
			assertTrue(file.exists());
			if (file.isFile()) {
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
				assertTrue("unexpected content for path " + path
						+ " in workDir. Expected: <" + expectedValue + ">"
						Arrays.equals(buffer
				nrFiles++;
			}
		}
		assertEquals("WorkDir has not the right size."
