
	@SuppressWarnings("unchecked")
	public List<String> getUntrackedFolders() {
		return (indexDiffFilter == null) ? (List<String>) Collections.EMPTY_LIST
				: indexDiffFilter.getUntrackedFolders();
	}
