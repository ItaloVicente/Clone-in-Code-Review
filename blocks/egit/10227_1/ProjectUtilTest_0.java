		assertTrue("Expected to find projects", nestedFound);
		assertEquals(2, nestedResult.size());
		assertThat(nestedResult, hasItem(new File(workingDir,
				"Project-1/.project")));
		assertThat(nestedResult, hasItem(new File(workingDir,
				"Project-1/Project-Nested/.project")));

		Collection<File> noNestedResult = new ArrayList<File>();
		boolean noNestedFound = ProjectUtil.findProjectFiles(noNestedResult,
				workingDir, false, new NullProgressMonitor());
		assertTrue("Expected to find projects", noNestedFound);
		assertEquals(1, noNestedResult.size());
		assertThat(noNestedResult, hasItem(new File(workingDir,
				"Project-1/.project")));
