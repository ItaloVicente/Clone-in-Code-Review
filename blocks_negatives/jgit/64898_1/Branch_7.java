		if (delete || deleteForce)
			delete(deleteForce);
		else {
			if (branches.size() > 2)
				throw die(CLIText.get().tooManyRefsGiven + new CmdLineParser(this).printExample(ExampleMode.ALL));

