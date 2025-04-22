		List<Ref> branches = getBranches();
		if (!branches.isEmpty()) {
			d.append(UIText.CommitMessageViewer_branches);
			int count = 0;
			for (Iterator<Ref> i = branches.iterator(); i.hasNext();) {
				Ref head = i.next();
				RevCommit p;
				try {
