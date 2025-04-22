
	private static String getFirstFailedRefName(BatchRefUpdate batch) {
		for (ReceiveCommand cmd : batch.getCommands()) {
			if (cmd.getResult() != ReceiveCommand.Result.OK)
				return cmd.getRefName();
		}
		return "";
	}
