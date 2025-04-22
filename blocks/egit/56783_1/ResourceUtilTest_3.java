
		connect(nested.getProject());

		result = ResourceUtil.getFileForLocation(location, false);
		assertThat(result, notNullValue());
		assertTrue("Returned IFile should exist", result.exists());
		assertThat(result.getProject(), is(project.getProject()));

		result = ResourceUtil.getFileForLocation(location, true);
		assertThat(result, notNullValue());
		assertTrue("Returned IFile should exist", result.exists());
		assertThat(result.getProject(), is(nested.getProject()));
