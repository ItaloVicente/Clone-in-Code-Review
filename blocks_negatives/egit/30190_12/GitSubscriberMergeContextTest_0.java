		new Git(repo).commit().setAuthor("JUnit", "junit@jgit.org")
				.setMessage("Initial commit").call();

		IContentType textType = Platform.getContentTypeManager()
				.getContentType("org.eclipse.core.runtime.text");
		textType.addFileSpec(SAMPLE_FILE_EXTENSION,
				IContentType.FILE_EXTENSION_SPEC);
