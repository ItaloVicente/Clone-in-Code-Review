	private RepositoryCommit[] getCommits(Ref end) {
		final RevWalk walk = new RevWalk(reader);
		try {
			walk.setRetainBody(true);
			walk.markStart(walk.parseCommit(update.getNewObjectId()));
			walk.markUninteresting(walk.parseCommit(end.getObjectId()));
			List<RepositoryCommit> commits = new ArrayList<RepositoryCommit>();
			for (RevCommit commit : walk)
				commits.add(new RepositoryCommit(repo, commit));
			return commits.toArray(new RepositoryCommit[commits.size()]);
		} catch (IOException e) {
			Activator.logError("Error parsing commits from push result", e); //$NON-NLS-1$
			return new RepositoryCommit[0];
		}
	}

	public Object[] getChildren(Object object) {
		if (children != null)
			return children;

		switch (update.getStatus()) {
		case OK:
			if (!isDelete()) {
				final Ref ref = getAdvertisedRemoteRef();
				if (ref != null) {
					children = getCommits(ref);
					break;
				}
			}
		default:
			children = super.getChildren(object);
		}
		return children;
	}

	public StyledString getStyledText(Object object) {
		StyledString styled = new StyledString();
		final String remote = getDstRefName();
		final String local = getSrcRefName();

		styled.append(Repository.shortenRefName(remote));

		if (!tag) {
			styled.append(": ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			styled.append(Repository.shortenRefName(local),
					StyledString.QUALIFIER_STYLER);
		}
		styled.append(' ');
		if (result.getURIs().size() > 1)
			styled.append(MessageFormat.format(
					UIText.RefUpdateElement_UrisDecoration, uri.toString()),
					StyledString.QUALIFIER_STYLER);

		styled.append(' ');
		switch (getStatus()) {
		case OK:
			if (update.isDelete())
				styled.append(UIText.PushResultTable_statusOkDeleted,
						StyledString.DECORATIONS_STYLER);
			else {
				final Ref oldRef = getAdvertisedRemoteRef();
				if (oldRef == null) {
					if (tag)
						styled.append(UIText.PushResultTable_statusOkNewTag,
								StyledString.DECORATIONS_STYLER);
					else
						styled.append(UIText.PushResultTable_statusOkNewBranch,
								StyledString.DECORATIONS_STYLER);
				} else {
					String separator = update.isFastForward() ? ".." : "..."; //$NON-NLS-1$ //$NON-NLS-2$
					styled.append(MessageFormat.format(
							UIText.RefUpdateElement_CommitRangeDecoration,
							update.getNewObjectId().abbreviate(7).name(),
							separator, oldRef.getObjectId().abbreviate(7)
									.name()), StyledString.DECORATIONS_STYLER);
					styled.append(' ');
					styled.append(MessageFormat.format(
							UIText.RefUpdateElement_CommitCountDecoration,
							Integer.valueOf(getChildren(this).length)),
							StyledString.COUNTER_STYLER);
				}
			}
			break;
		case UP_TO_DATE:
			styled.append(UIText.PushResultTable_statusUpToDate,
					StyledString.DECORATIONS_STYLER);
			break;
		case NON_EXISTING:
			styled.append(UIText.PushResultTable_statusNoMatch,
					StyledString.DECORATIONS_STYLER);
			break;
		case REJECTED_NODELETE:
		case REJECTED_NONFASTFORWARD:
		case REJECTED_REMOTE_CHANGED:
			styled.append(UIText.PushResultTable_statusRejected,
					StyledString.DECORATIONS_STYLER);
			break;
		case REJECTED_OTHER_REASON:
			styled.append(UIText.PushResultTable_statusRemoteRejected,
					StyledString.DECORATIONS_STYLER);
			break;
		default:
			break;
		}
		return styled;
