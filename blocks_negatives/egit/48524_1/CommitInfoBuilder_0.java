		try (RevWalk rw = new RevWalk(db)) {
			List<Ref> branches = getBranches(commit, allRefs, db);
			if (!branches.isEmpty()) {
				d.append(UIText.CommitMessageViewer_branches);
				int count = 0;
				for (Iterator<Ref> i = branches.iterator(); i.hasNext();) {
					Ref head = i.next();
					RevCommit p;
					p = rw.parseCommit(head.getObjectId());
					addLink(d, formatHeadRef(head), styles, p);
					if (i.hasNext()) {
						if (count++ <= MAXBRANCHES) {
							d.append(", "); //$NON-NLS-1$
						} else {
							d.append(NLS.bind(UIText.CommitMessageViewer_MoreBranches, Integer.valueOf(branches.size() - MAXBRANCHES)));
							break;
