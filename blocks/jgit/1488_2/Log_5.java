		final RevTree a = c.getParent(0).getTree();
		final RevTree b = c.getTree();

		if (showNameAndStatusOnly)
			Diff.nameStatus(out
		else {
			diffFmt.format(a
