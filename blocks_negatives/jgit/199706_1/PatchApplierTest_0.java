		protected void checkBinary(Result result, int numberOfFiles)
				throws Exception {
			assertEquals(numberOfFiles, result.getPaths().size());
			if (inCore) {
				assertArrayEquals(postImage,
						readBlob(result.getTreeId(), result.getPaths().get(0)));
			} else {
				File f = new File(db.getWorkTree(), name);
				assertArrayEquals(postImage, Files.readAllBytes(f.toPath()));
