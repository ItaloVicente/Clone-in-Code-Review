	private void addUpdateBatchCommands(FetchResult result
			BatchRefUpdate batch) throws TransportException {
		Map<String
		for (TrackingRefUpdate u : localUpdates) {
			ObjectId existing = refs.get(u.getLocalName());
			if (existing == null) {
				refs.put(u.getLocalName()
				result.add(u);
				batch.addCommand(u.asReceiveCommand());
			} else if (!existing.equals(u.getNewObjectId())) {
				throw new TransportException(MessageFormat
						.format(JGitText.get().duplicateRef
			}
		}
	}

