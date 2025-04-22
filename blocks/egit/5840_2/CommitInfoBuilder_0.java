		try {
			List<Ref> branches = getBranches(commit, allRefs, db);
			if (!branches.isEmpty()) {
				d.append(UIText.CommitMessageViewer_branches);
				d.append(": "); //$NON-NLS-1$
				int count = 0;
				for (Iterator<Ref> i = branches.iterator(); i.hasNext();) {
					Ref head = i.next();
					RevCommit p;
