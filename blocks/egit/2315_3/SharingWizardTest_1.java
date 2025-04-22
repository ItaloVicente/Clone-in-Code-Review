		FileRepository repo3 = new FileRepository(new File(
				createProject(projectName3), ".git"));
		repo3.create();
		Git git = new Git(repo3);
		git.add().addFilepattern(".").call();
		git.commit().setAuthor("A U Thior", "au.thor@example.com").setMessage("Created Project 3").call();
		repo3.close();

