	@Test
	public void testWithNestedProjects() throws Exception {
		TestProject nested = new TestProject(true, "Project-1/Project-2");
		try {
			project.createFolder("Project-2/please");
			IFile ignoreme = nested.createFile("please/ignoreme", new byte[0]);
			IgnoreOperation operation = executeIgnore(ignoreme.getLocation());
			String content = nested.getFileContent("please/.gitignore");
			assertEquals("/ignoreme\n", content);
			assertFalse(operation.isGitignoreOutsideWSChanged());
		} finally {
			nested.dispose();
		}
	}

