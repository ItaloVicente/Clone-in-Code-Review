		Git git = new Git(db);
		writeTrashFile("file_0.txt", "content_0_1");
		git.add().addFilepattern("file_0.txt").call();
		git.commit().setMessage("commit_1").call();

		writeTrashFile("file_0.txt", "content_0_2");
		String expectedFilePath1 = "some_directory/file_1.txt";
		writeTrashFile(expectedFilePath1, "content_1_2");
		String expectedFilePath2 = "some_directory/file_2.txt";
		writeTrashFile(expectedFilePath2, "content_2_2");
	        String expectedFilePath3 = "some_directory/nested_directory/file_3.txt";
		writeTrashFile(expectedFilePath3, "content_3_2");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit_2").call();
		git.archive().setOutputStream(new MockOutputStream())
				.setFormat(format.SUFFIXES.get(0))
				.setTree(git.getRepository().resolve("HEAD"))
				.setPaths("some_directory/").call();

		assertEquals(UNEXPECTED_ARCHIVE_SIZE, 5, format.size());
		assertEquals(UNEXPECTED_FILE_CONTENTS, "content_1_2", format.getByPath(expectedFilePath1));
		assertEquals(UNEXPECTED_FILE_CONTENTS, "content_2_2", format.getByPath(expectedFilePath2));
		assertEquals(UNEXPECTED_FILE_CONTENTS, "content_3_2", format.getByPath(expectedFilePath3));
		assertNull(UNEXPECTED_TREE_CONTENTS, format.getByPath("some_directory"));
		assertNull(UNEXPECTED_TREE_CONTENTS, format.getByPath("some_directory/nested_directory"));
