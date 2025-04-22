	@Option(name = "--create-force"
	public void createForce(String args) {
		createForce = true;
		if (args != null && !args.isEmpty()) {
			if (branchAndStartPoint.length > 2) {
				throw die(
						CLIText.get().tooManyRefsGiven + new CmdLineParser(this)
								.printExample(ExampleMode.ALL));
			}
			if (branchAndStartPoint.length == 2) {
				branch = branchAndStartPoint[0];
				otherBranch = branchAndStartPoint[1];
			} else {
				branch = branchAndStartPoint[0];
			}
		}
	}
