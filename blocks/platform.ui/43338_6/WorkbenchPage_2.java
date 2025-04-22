	@PreDestroy
	public void saveShowInMruPartIdsList() {
		if (!mruPartIds.isEmpty()) {
			StringBuilder mruList = new StringBuilder();
			for (String partId : mruPartIds) {
				if (mruList.length() != 0)
					mruList.append(MRU_LIST_SEPARATOR);
				mruList.append(partId);
			}
			getWindowModel().getPersistedState().put(IWorkbenchConstants.TAG_MRU_LIST, mruList.toString());
		}
	}

