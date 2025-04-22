	public GitModelObject[] getChildren() {
		List<GitModelObject> restult = new ArrayList<GitModelObject>();
		try {
			if (gsds.size() == 1) {
				GitSynchronizeData gsd = gsds.iterator().next();
				GitModelRepository repoModel = new GitModelRepository(gsd);

				for (GitModelObject obj : repoModel.getChildren())
					restult.add(obj);
			} else
				for (GitSynchronizeData data : gsds)
						restult.add(new GitModelRepository(data));
		} catch (IOException e) {
