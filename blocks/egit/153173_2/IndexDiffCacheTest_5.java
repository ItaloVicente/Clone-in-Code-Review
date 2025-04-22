		runInWorkspace(() -> {
			project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
			project.createFolder("sub");
			project.createFile("sub/ignore", new byte[] {});
			return null;
		});
