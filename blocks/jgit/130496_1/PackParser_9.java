	public List<GitmoduleEntry> getGitmodulesEntries() {
		if (objCheck != null) {
			return objCheck.getGitsubmodules();
		}

		return new ArrayList<>();
	}

