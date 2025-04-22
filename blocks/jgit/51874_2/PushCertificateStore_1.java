		ObjectId newId = write(null);
		if (newId == null) {
			return;
		}
		batch.addCommand(new ReceiveCommand(
				commit != null ? commit : ObjectId.zeroId()
	}

	public void saveMatching(BatchRefUpdate batch) throws IOException {
		ObjectId newId = write(batch.getCommands());
