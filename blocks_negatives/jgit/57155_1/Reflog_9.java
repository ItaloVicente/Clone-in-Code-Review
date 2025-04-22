		ReflogCommand cmd = new Git(db).reflog();
		if (ref != null)
			cmd.setRef(ref);
		Collection<ReflogEntry> entries = cmd.call();
		int i = 0;
		for (ReflogEntry entry : entries) {
			outw.println(toString(entry, i++));
