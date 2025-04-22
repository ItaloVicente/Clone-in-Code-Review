		try (Git git = new Git(db)) {
			writeTrashFile("file_1.txt"
			git.add().addFilepattern("file_1.txt").call();
			git.commit().setMessage("create file").call();

			writeTrashFile("file_1.txt"
			writeTrashFile("file_2.txt"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("updated file").call();

			git.archive().setOutputStream(new MockOutputStream())
					.setFormat(format.SUFFIXES.get(0))
					.setTree(git.getRepository().resolve("HEAD")).call();

			assertEquals(UNEXPECTED_ARCHIVE_SIZE
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertEquals(UNEXPECTED_FILE_CONTENTS
		}
