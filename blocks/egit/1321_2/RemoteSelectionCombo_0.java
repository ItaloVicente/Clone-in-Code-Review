	public void setDefautlValue(String remote, String ref) {
		int i = 0;
		for (; i < syncRepos.size(); i++)
			if (syncRepos.get(i).getName().equals(remote))
				break;

		if (i == syncRepos.size())
			return;	// repository name not found

		remotesCombo.select(i);
		fillRefsCombo();

		List<SyncRefEntity> refList = syncRepos.get(i).getRefList();
		i = 0;
		for (; i < refList.size(); i++)
			if (refList.get(i).getDescription().equals(ref))
				break;

		if (i == syncRepos.size())
			return;	// ref name not found
		refsCombo.select(i);
	}

