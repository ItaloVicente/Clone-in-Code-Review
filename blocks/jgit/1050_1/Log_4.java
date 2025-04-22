
	private void showDiff(RevCommit c) throws IOException {
		final TreeWalk tw = new TreeWalk(db);
		tw.reset();
		tw.setRecursive(true);
		tw.addTree(c.getParent(0).getTree());
		tw.addTree(c.getTree());
		tw.setFilter(AndTreeFilter.create(TreeFilter.ANY_DIFF

		List<DiffEntry> files = DiffEntry.scan(tw);
		if (detectRenames) {
			RenameDetector rd = new RenameDetector(db);
			rd.addAll(files);
			files = rd.compute(new TextProgressMonitor());
		}

		if (showNameAndStatusOnly) {
			Diff.nameStatus(out

		} else {
			out.flush();
			BufferedOutputStream o = new BufferedOutputStream(System.out);
			diffFmt.format(o
			o.flush();
		}
	}
