		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.b").call();
			git.add().addFilepattern("a.c").call();
			git.add().addFilepattern("a=c").call();
			git.add().addFilepattern("a=d").call();
		}
