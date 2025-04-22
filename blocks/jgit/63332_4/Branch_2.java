	@Option(name = "--move"
	public void moveRename(String args) {
		rename = true;
		if (args == null) {
			throw die(CLIText.get().branchNameRequired
					+ new CmdLineParser(this).printExample(ExampleMode.ALL));
		}
		if (!args.trim().isEmpty()) {
			if (currentAndNew.length > 2) {
				throw die(CLIText.get().tooManyRefsGiven
						+ new CmdLineParser(this).printExample(ExampleMode.ALL));
			}
			if (currentAndNew.length == 0) {
				throw die(CLIText.get().branchNameRequired
						+ new CmdLineParser(this).printExample(ExampleMode.ALL));
			}
			if (currentAndNew.length == 2) {
				branch = currentAndNew[0];
				otherBranch = currentAndNew[1];
			} else {
				branch = currentAndNew[0];
			}
		}
	}
