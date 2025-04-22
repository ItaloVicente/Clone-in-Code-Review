		if (db == null || nonNullRepo().isBare() || !isFile)
			return;

		File file = new File(nonNullRepo().getWorkTree()
		EolStreamType streamType = workTreeUpdater.detectCheckoutStreamType(attributes);
		String smudgeCommand = tw.getSmudgeCommand(attributes);
		workTreeUpdater.deleteFile(path
