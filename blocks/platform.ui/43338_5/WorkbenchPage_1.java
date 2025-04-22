	private void restoreShowInMruPartIdsList() {
		String mruList = getWindowModel().getPersistedState().get(IWorkbenchConstants.TAG_MRU_LIST);
		if (mruList != null && !mruList.isEmpty()) {
			mruPartIds.addAll(Arrays.asList(mruList.split(MRU_LIST_SEPARATOR)));
		}
	}

