	@PreDestroy
	public void saveShowInMruPartIdsList() {
		if (!mruPartIds.isEmpty()) {
			StringBuilder mruList = new StringBuilder();
			for (Iterator<String> it = mruPartIds.iterator(); it.hasNext();) {
				mruList.append(it.next());
				if (it.hasNext())
					mruList.append(MRU_LIST_SEPARATOR);
			}
			getWindowModel().getPersistedState().put(IWorkbenchConstants.TAG_MRU_LIST, mruList.toString());
		}
	}

