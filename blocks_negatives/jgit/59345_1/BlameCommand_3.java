		WorkingTreeOptions workingTreeOptions = getRepository().getConfig()
				.get(WorkingTreeOptions.KEY);
		AutoCRLF autoCRLF = workingTreeOptions.getAutoCRLF();
		switch (autoCRLF) {
		case FALSE:
		case INPUT:
			rawText = new RawText(inTree);
			break;
		case TRUE:
			EolCanonicalizingInputStream in = new EolCanonicalizingInputStream(
