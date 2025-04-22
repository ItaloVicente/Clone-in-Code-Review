		assertEquals(true,
				Repository.normalizeBranchName("__@#$@#$@$____   _")
						.equals(""));

		assertEquals(true,
				Repository.normalizeBranchName("~`!@#$%^&*()_+}]{[|\\\";?>.<,/")
						.equals(""));

		assertEquals(true,
				Repository.normalizeBranchName("Bug 12345 :::: Hello World")
						.equals("Bug_12345-Hello_World"));

		assertEquals(true,
				Repository.normalizeBranchName("Bug 12345 :::: Hello::: World")
						.equals("Bug_12345-Hello-_World"));

		assertEquals(true,
				Repository.normalizeBranchName(":::Bug 12345 - Hello World")
						.equals("Bug_12345-Hello_World"));

		assertEquals(true,
				Repository.normalizeBranchName("---Bug 12345 - Hello World")
						.equals("Bug_12345-Hello_World"));

		assertEquals(true,
				Repository.normalizeBranchName("Bug 12345 ---- Hello --- World")
						.equals("Bug_12345-Hello-World"));

		assertEquals(true, Repository.normalizeBranchName(null) == null);
