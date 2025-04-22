		configure();
		final String name = getClass().getName() + "." + getName();
		recursiveDelete(trashParent, true, name, false); // Cleanup old failed stuff
		trash = new File(trashParent,trash+System.currentTimeMillis()+"."+(testcount++));
		trash_git = new File(trash, ".git").getCanonicalFile();
		if (shutdownhook == null) {
			shutdownhook = new Thread() {
				@Override
				public void run() {
					System.gc();
					for (Runnable r : shutDownCleanups)
						r.run();
					recursiveDelete(trashParent, false, null, false);
				}
			};
			Runtime.getRuntime().addShutdownHook(shutdownhook);
		}

		final MockSystemReader mockSystemReader = new MockSystemReader();
		mockSystemReader.userGitConfig = new FileBasedConfig(new File(
				trash_git, "usergitconfig"));
		SystemReader.setInstance(mockSystemReader);

		db = new Repository(trash_git);
		db.create();
