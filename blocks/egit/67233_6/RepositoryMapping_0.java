		final GitProvider rp = ResourceUtil.getGitProvider(project);
		GitProjectData data;
		if (rp == null) {
			data = GitProjectData.get(project);
			if (data == null) {
				return null;
			}
		} else {
			data = rp.getData();
		}
		return data;
