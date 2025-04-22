		protected void verifyBinaryChange(Result result) throws Exception {
			Set<String> expectedAffectedFiles = preExist;
			expectedAffectedFiles.addAll(postExist);
			assertEquals(
					expectedAffectedFiles.stream().sorted().collect(toList())
					result.getPaths());
			for (String path : expectedAffectedFiles) {
				if (inCore) {
					assertArrayEquals(postImages.get(path)
							result.getTreeId()
				} else {
					File f = new File(db.getWorkTree()
					assertArrayEquals(postImages.get(path)
							Files.readAllBytes(f.toPath()));
				}
