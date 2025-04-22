		try (Git git = new Git(db)) {
			writeTrashFile("file_0.txt"
			git.add().addFilepattern("file_0.txt").call();
			git.commit().setMessage("commit_1").call();

			writeTrashFile("file_0.txt"
			String expectedFilePath1 = "some_directory/file_1.txt";
			writeTrashFile(expectedFilePath1
			String expectedFilePath2 = "some_directory/file_2.txt";
			writeTrashFile(expectedFilePath2
		        String expectedFilePath3 = "some_directory/nested_directory/file_3.txt";
			writeTrashFile(expectedFilePath3
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit_2").call();
			git.archive().setOutputStream(new MockOutputStream())
					.setFormat(format.SUFFIXES.get(0))
					.setTree(git.getRepository().resolve("HEAD"))
					.setPaths("some_directory/").call();

			assertEquals(UNEXPECTED_ARCHIVE_SIZE
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertNull(UNEXPECTED_TREE_CONTENTS
			assertNull(UNEXPECTED_TREE_CONTENTS
		}
