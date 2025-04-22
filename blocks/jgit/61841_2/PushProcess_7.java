	private Map<String
		for (RemoteRefUpdate rru : toPush.values()) {
			if (rru.getStatus() == Status.NOT_ATTEMPTED) {
				rru.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
				rru.setMessage(JGitText.get().transactionAborted);
			}
		}
		return Collections.emptyMap();
	}

