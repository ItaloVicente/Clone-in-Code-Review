	@Test
	public void testParsePack1ReadsObjectSizes() throws IOException {
		File packFile = JGitTestUtil.getTestResourceFile(
				"pack-34be9032ac282b11fa9babdc2b2a93ca996c9c2f.pack");

		Map<String
		expected.put("540a36d136cf413e4b064c2b0e0a4db60f77feab"
				Long.valueOf(191));
		expected.put("c59759f143fb1fe21c197981df75a7ee00290799"
				Long.valueOf(240));
		expected.put("82c6b885ff600be425b4ea96dee75dca255b69e7"
				Long.valueOf(245));

		expected.put("4b825dc642cb6eb9a060e54bf8d69288fbee4904"
		expected.put("902d5476fa249b7abc9d84c611577a81381f0327"
				Long.valueOf(35));
		expected.put("aabf2ffaec9b497f0950352b3e582d73035c2035"
				Long.valueOf(35));

		expected.put("6ff87c4664981e4397625791c8ea3bbb5f2279a3"
				Long.valueOf(18787));

		expected.put("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259"


		try (InputStream is = new FileInputStream(packFile)) {
			ObjectDirectoryPackParser p = (ObjectDirectoryPackParser) index(is);
			p.parse(NullProgressMonitor.INSTANCE);
			List<PackedObjectInfo> parsedObjects = p.getSortedObjectList(null);
			for (PackedObjectInfo objInfo: parsedObjects) {
				assertEquals(objInfo.getName()
						expected.get(objInfo.getName()).longValue());
			}
		}
	}

