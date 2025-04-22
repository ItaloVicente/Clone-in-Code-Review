		contents = testUtils.slurpAndClose(file1.getContents());
		assertEquals("Hello world 1", contents);

		contents = testUtils.slurpAndClose(file2.getContents());
		assertEquals("Hello world 2", contents);
	}
