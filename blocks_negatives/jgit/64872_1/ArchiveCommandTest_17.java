		Git git = new Git(db);
		writeTrashFile("file_1.txt", "content_1_1");
		git.add().addFilepattern("file_1.txt").call();
		RevCommit first = git.commit().setMessage("create file").call();

		writeTrashFile("file_1.txt", "content_1_2");
		String expectedFilePath = "some_directory/file_2.txt";
		writeTrashFile(expectedFilePath, "content_2_2");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("updated file").call();

		Map<String, Object> options = new HashMap<>();
		Integer opt = Integer.valueOf(42);
		options.put("foo", opt);
		MockOutputStream out = new MockOutputStream();
		git.archive().setOutputStream(out)
				.setFormat(format.SUFFIXES.get(0))
				.setFormatOptions(options)
				.setTree(first)
				.setPaths("file_1.txt").call();

		assertEquals(opt.intValue(), out.getFoo());
		assertEquals(UNEXPECTED_ARCHIVE_SIZE, 1, format.size());
		assertEquals(UNEXPECTED_FILE_CONTENTS, "content_1_1", format.getByPath("file_1.txt"));
