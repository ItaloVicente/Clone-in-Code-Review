		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.b").addFilepattern("a.c")
					.addFilepattern("a/b.b/b").addFilepattern("a/b")
					.addFilepattern("a/c").addFilepattern("a=c")
					.addFilepattern("a=d").call();
		}
