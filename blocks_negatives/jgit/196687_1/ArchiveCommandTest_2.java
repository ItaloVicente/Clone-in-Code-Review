			assertEquals(UNEXPECTED_ARCHIVE_SIZE, 5, format.size());
			assertEquals(UNEXPECTED_FILE_CONTENTS, "content_1_2", format.getByPath(expectedFilePath1));
			assertEquals(UNEXPECTED_FILE_CONTENTS, "content_2_2", format.getByPath(expectedFilePath2));
			assertEquals(UNEXPECTED_FILE_CONTENTS, "content_3_2", format.getByPath(expectedFilePath3));
			assertNull(UNEXPECTED_TREE_CONTENTS, format.getByPath("some_directory"));
			assertNull(UNEXPECTED_TREE_CONTENTS, format.getByPath("some_directory/nested_directory"));
