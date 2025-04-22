		try (Git git = new Git(db)) {
			ReflogCommand cmd = git.reflog();
			if (ref != null)
				cmd.setRef(ref);
			Collection<ReflogEntry> entries = cmd.call();
			int i = 0;
			for (ReflogEntry entry : entries) {
				outw.println(toString(entry
			}
