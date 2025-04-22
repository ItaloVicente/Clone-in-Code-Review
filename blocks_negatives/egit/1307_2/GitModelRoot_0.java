	public GitModelRepository[] getChildren() {
		List<GitModelRepository> restult = new ArrayList<GitModelRepository>();
		for (GitSynchronizeData data : gsds) {
			try {
				restult.add(new GitModelRepository(data));
			} catch (IOException e) {
