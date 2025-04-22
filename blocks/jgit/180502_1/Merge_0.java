	private ContentMergeStrategy contentStrategy = null;

	@Option(name = "--strategy-option"
			metaVar = "metaVar_extraArgument"
	void extraArg(String name) {
		if (ContentMergeStrategy.OURS.name().equalsIgnoreCase(name)) {
			contentStrategy = ContentMergeStrategy.OURS;
		} else if (ContentMergeStrategy.THEIRS.name().equalsIgnoreCase(name)) {
			contentStrategy = ContentMergeStrategy.THEIRS;
		} else {
			throw die(MessageFormat.format(CLIText.get().unknownExtraArgument
		}
	}

