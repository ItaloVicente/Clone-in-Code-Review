		try (Git git = new Git(db)) {
			writeTrashFile("file_1.txt"
			git.add().addFilepattern("file_1.txt").call();
			RevCommit first = git.commit().setMessage("create file").call();

			writeTrashFile("file_1.txt"
			String expectedFilePath = "some_directory/file_2.txt";
			writeTrashFile(expectedFilePath
			git.add().addFilepattern(".").call();
			git.commit().setMessage("updated file").call();

			Map<String
			Integer opt = Integer.valueOf(42);
			options.put("foo"
			MockOutputStream out = new MockOutputStream();
			git.archive().setOutputStream(out)
					.setFormat(format.SUFFIXES.get(0))
					.setFormatOptions(options)
					.setTree(first)
					.setPaths("file_1.txt").call();

			assertEquals(opt.intValue()
			assertEquals(UNEXPECTED_ARCHIVE_SIZE
			assertEquals(UNEXPECTED_FILE_CONTENTS
		}
