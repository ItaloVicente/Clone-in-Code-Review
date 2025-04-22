		IFile file[] = { null };
		runInWorkspace(() -> {
			project.createFile(".gitignore", "ignore\n".getBytes("UTF-8"));
			project.createFolder("sub");
			file[0] = project.createFile("sub/ignore", new byte[] {});
			return null;
		});
