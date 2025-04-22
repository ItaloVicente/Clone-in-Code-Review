	private void doRefreshGroup(GroupInfo info) {
		for (IJobProgressManagerListener listener : listeners) {
			listener.refreshGroup(info);
		}
	}

