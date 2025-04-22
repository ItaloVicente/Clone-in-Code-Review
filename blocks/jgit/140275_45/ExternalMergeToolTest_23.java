		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.merge(local
				base
				noToolHandler);
		return result;
	}

	private void assumeMergeToolIsAvailable(
			CommandLineMergeTool autoMergingTool) {
		boolean isAvailable = ExternalToolUtils.isToolAvailable(db.getFS()
				db.getDirectory()
		assumeTrue("Assuming external tool is available: "
				+ autoMergingTool.name()
