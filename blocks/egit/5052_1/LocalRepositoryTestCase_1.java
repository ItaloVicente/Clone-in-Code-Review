		touchAndSubmit(newContent, commitMessage);
	}

	protected static void touchAndSubmit(String newContent, String commitMessage)
			throws Exception {
		IFile file = touch(newContent);
