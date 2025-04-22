		List<Ref> branches = getBranches();
		if (!branches.isEmpty()) {
			d.append(UIText.CommitMessageViewer_branches);
			d.append(": "); //$NON-NLS-1$
			for (Iterator<Ref> i = branches.iterator(); i.hasNext(); ) {
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
			d.append(": "); //$NON-NLS-1$
			d.append(tagsString);
			d.append(LF);
		}

		try {
			Ref followingTag = getNextTag(false);
			if (followingTag != null) {
				d.append(UIText.CommitMessageViewer_follows);
				d.append(": "); //$NON-NLS-1$
				RevCommit p = new RevWalk(db).parseCommit(followingTag.getObjectId());
				addLink(d, formatTagRef(followingTag), styles, p);
				d.append(LF);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		try {
			Ref precedingTag = getNextTag(true);
			if (precedingTag != null) {
				d.append(UIText.CommitMessageViewer_precedes);
				d.append(": "); //$NON-NLS-1$
				RevCommit p = new RevWalk(db).parseCommit(precedingTag.getObjectId());
				addLink(d, formatTagRef(precedingTag), styles, p);
				d.append(LF);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

