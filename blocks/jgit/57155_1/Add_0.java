		try (Git git = new Git(db)) {
			AddCommand addCmd = git.add();
			addCmd.setUpdate(update);
			for (String p : filepatterns)
				addCmd.addFilepattern(p);
			addCmd.call();
		}
