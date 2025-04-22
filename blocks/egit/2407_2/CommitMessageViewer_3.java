		d.append(UIText.CommitMessageViewer_branches);
		d.append(": "); //$NON-NLS-1$
		for (Iterator<Ref> i = getBranches().iterator(); i.hasNext(); ) {
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

		d.append(UIText.CommitMessageViewer_tags);
		d.append(": "); //$NON-NLS-1$
		d.append(getTagsString());
		d.append(LF);

		d.append(UIText.CommitMessageViewer_follows);
		d.append(": "); //$NON-NLS-1$
		try {
			Ref tag  = getNextTag(false);
			if (tag != null) {
				RevCommit p = new RevWalk(db).parseCommit(tag.getObjectId());
				addLink(d, formatTagRef(tag), styles, p);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
		d.append(LF);

		d.append(UIText.CommitMessageViewer_precedes);
		d.append(": "); //$NON-NLS-1$
		try {
			Ref tag  = getNextTag(true);
			if (tag != null) {
				RevCommit p = new RevWalk(db).parseCommit(tag.getObjectId());
				addLink(d, formatTagRef(tag), styles, p);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
		d.append(LF);

