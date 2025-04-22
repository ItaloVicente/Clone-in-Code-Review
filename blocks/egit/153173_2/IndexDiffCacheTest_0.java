		IFile[] fileA = { null };
		runInWorkspace(() -> {
			project.createFolder("folder");
			project.createFolder("folder/a");
			project.createFolder("folder/b");
			fileA[0] = project.createFile("folder/a/file", new byte[] {});
			project.createFile("folder/b/file", new byte[] {});
			return null;
		});

		prepareCacheEntry();
