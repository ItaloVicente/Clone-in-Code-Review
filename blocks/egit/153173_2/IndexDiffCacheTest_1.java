		runInWorkspace(() -> {
			project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
			project.createFolder("ignore");
			project.createFile("ignore/file.txt", new byte[] {});
			project.createFolder("sub");
			return null;
		});
