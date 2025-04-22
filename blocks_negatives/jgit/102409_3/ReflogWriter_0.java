	    return repo.getConfig().get(CoreConfig.key(repo)).isLogAllRefUpdates();
	}

	private boolean shouldAutoCreateLog(String refName) {
		return refName.equals(HEAD)
				|| refName.startsWith(R_HEADS)
				|| refName.startsWith(R_REMOTES)
				|| refName.startsWith(R_NOTES);
