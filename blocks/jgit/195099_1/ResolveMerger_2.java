		File file = (isFile && db != null && !nonNullRepo().isBare())
				? new File(nonNullRepo().getWorkTree()
				: null;
		EolStreamType streamType = workTreeUpdater.detectCheckoutStreamType(attributes);
		String smudgeCommand = tw.getSmudgeCommand(attributes);
		workTreeUpdater.deleteFile(path
