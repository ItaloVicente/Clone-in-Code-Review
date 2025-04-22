
		final PersonIdent author = commit.getAuthorIdent();
		final PersonIdent committer = commit.getCommitterIdent();
		d.append(UIText.CommitMessageViewer_commit);
		d.append(SPACE);
		d.append(commit.getId().name());
		d.append(LF);

		if (author != null) {
			d.append(UIText.CommitMessageViewer_author);
			d.append(author.getName());
			d.append(author.getEmailAddress());
			d.append(fmt.format(author.getWhen()));
			d.append(LF);
		}

		if (committer != null) {
			d.append(UIText.CommitMessageViewer_committer);
			d.append(committer.getName());
			d.append(committer.getEmailAddress());
			d.append(fmt.format(committer.getWhen()));
			d.append(LF);
		}

		for (int i = 0; i < commit.getParentCount(); i++) {
			final RevCommit p = commit.getParent(i);
			d.append(UIText.CommitMessageViewer_parent);
			addLink(d, styles, p);
			d.append(p.getShortMessage());
			d.append(LF);
		}

		for (int i = 0; i < commit.getChildCount(); i++) {
			final RevCommit p = commit.getChild(i);
			d.append(UIText.CommitMessageViewer_child);
			addLink(d, styles, p);
			d.append(p.getShortMessage());
			d.append(LF);
		}

		List<Ref> branches = getBranches();
		if (!branches.isEmpty()) {
			d.append(UIText.CommitMessageViewer_branches);
			for (Iterator<Ref> i = branches.iterator(); i.hasNext();) {
				Ref head = i.next();
				RevCommit p;
				try {
					p = new RevWalk(db).parseCommit(head.getObjectId());
					addLink(d, formatHeadRef(head), styles, p);
					if (i.hasNext())
						d.append(", "); //$NON-NLS-1$
				} catch (MissingObjectException e) {
					Activator.logError(e.getMessage(), e);
				} catch (IncorrectObjectTypeException e) {
					Activator.logError(e.getMessage(), e);
				} catch (IOException e) {
					Activator.logError(e.getMessage(), e);
				}
			}
			d.append(LF);
		}

		String tagsString = getTagsString();
		if (tagsString.length() > 0) {
			d.append(UIText.CommitMessageViewer_tags);
			d.append(tagsString);
			d.append(LF);
		}

