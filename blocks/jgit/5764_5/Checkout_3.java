		if (createBranch) {
			final ObjectId head = db.resolve(Constants.HEAD);
			if (head == null)
				throw die(CLIText.get().onBranchToBeBorn);
		}

