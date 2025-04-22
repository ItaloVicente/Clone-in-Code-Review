	@Test
	public void testParsePack2ReadsObjectSizes() throws IOException {
		File packFile = JGitTestUtil.getTestResourceFile(
				"pack-df2982f284bbabb6bdb59ee3fcc6eb0983e20371.pack");
		Map<String
		expected.put("d0114ab8ac326bab30e3a657a0397578c5a1af88"
				Long.valueOf(222));
		expected.put("f73b95671f326616d66b2afb3bdfcdbbce110b44"
				Long.valueOf(221));
		expected.put("be9b45333b66013bde1c7314efc50fabd9b39c6d"
				Long.valueOf(94));

		try (InputStream is = new FileInputStream(packFile)) {
			ObjectDirectoryPackParser p = (ObjectDirectoryPackParser) index(is);
			p.parse(NullProgressMonitor.INSTANCE);
			List<PackedObjectInfo> parsedObjects = p.getSortedObjectList(null);
			int assertedObjs = 0;
			for (PackedObjectInfo objInfo : parsedObjects) {
				if (!expected.containsKey(objInfo.getName())) {
					continue;
				}
				assertEquals(objInfo.getName()
						expected.get(objInfo.getName()).longValue());
				assertedObjs += 1;
			}
			assertEquals(assertedObjs
		}
	}

