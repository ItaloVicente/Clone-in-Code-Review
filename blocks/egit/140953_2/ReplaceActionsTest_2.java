		touchAndSubmit(null);
		String newContent = getTestFileContent();
		assertThat(newContent, not(initialContent));
		touch("Something else");
		String changedContent = getTestFileContent();
		assertThat(changedContent, not(initialContent));
		assertThat(changedContent, not(newContent));
