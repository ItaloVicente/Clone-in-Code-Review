		project2 = new TestProject(true, "Project-2");
		gitDir2 = new File(project2.getProject()
				.getLocationURI().getPath(), Constants.DOT_GIT);
		testRepo2 = new TestRepository(gitDir2);
		testRepo2.connect(project2.getProject());
		testRepo2.commit("initial commit repo 2");
