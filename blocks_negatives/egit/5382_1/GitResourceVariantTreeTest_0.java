		IProject secondIProject = secondProject.project;
		new ConnectProviderOperation(secondIProject, gitDir).execute(null);
		new Git(repo).commit().setAuthor("JUnit", "junit@egit.org")
				.setMessage("Initial commit").call();
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, HEAD,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);
