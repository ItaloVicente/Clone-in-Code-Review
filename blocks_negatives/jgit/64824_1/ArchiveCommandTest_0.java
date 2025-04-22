		Git git = new Git(db);
		writeTrashFile("file_1.txt", "content_1_1");
		git.add().addFilepattern("file_1.txt").call();
		git.commit().setMessage("create file").call();

		writeTrashFile("file_1.txt", "content_1_2");
		writeTrashFile("file_2.txt", "content_2_2");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("updated file").call();

		git.archive().setOutputStream(new MockOutputStream())
				.setFormat(format.SUFFIXES.get(0))
				.setTree(git.getRepository().resolve("HEAD")).call();

		assertEquals(UNEXPECTED_ARCHIVE_SIZE, 2, format.size());
		assertEquals(UNEXPECTED_FILE_CONTENTS, "content_1_2", format.getByPath("file_1.txt"));
		assertEquals(UNEXPECTED_FILE_CONTENTS, "content_2_2", format.getByPath("file_2.txt"));
