	private void format(final List<StyleRange> styles, final StringBuilder d,
			IProgressMonitor monitor) throws InterruptedException,
			InvocationTargetException {
		boolean trace = GitTraceLocation.HISTORYVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
		monitor
				.setTaskName(UIText.CommitMessageViewer_FormattingMessageTaskName);
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

		if (Activator.getDefault().getPreferenceStore().getBoolean(
				UIPreferences.HISTORY_SHOW_TAG_SEQUENCE)) {
			try {
				monitor
						.setTaskName(UIText.CommitMessageViewer_GettingPreviousTagTaskName);
				Ref followingTag = getNextTag(false, monitor);
				if (followingTag != null) {
					d.append(UIText.CommitMessageViewer_follows);
					RevCommit p = new RevWalk(db).parseCommit(followingTag
							.getObjectId());
					addLink(d, formatTagRef(followingTag), styles, p);
					d.append(LF);
				}
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}

			try {
				monitor
						.setTaskName(UIText.CommitMessageViewer_GettingNextTagTaskName);
				Ref precedingTag = getNextTag(true, monitor);
				if (precedingTag != null) {
					d.append(UIText.CommitMessageViewer_precedes);
					RevCommit p = new RevWalk(db).parseCommit(precedingTag
							.getObjectId());
					addLink(d, formatTagRef(precedingTag), styles, p);
					d.append(LF);
				}
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
		}

		makeGrayText(d, styles);
		d.append(LF);
		String msg = commit.getFullMessage();
		if (fill) {
			Matcher spm = p.matcher(msg);
			if (spm.find()) {
				String subMsg = msg.substring(0, spm.end());
				msg = subMsg.replaceAll("([\\w.,; \t])\n(\\w)", "$1 $2") //$NON-NLS-1$ //$NON-NLS-2$
						+ msg.substring(spm.end());
			}
		}
		int h0 = d.length();
		d.append(msg);
		d.append(LF);

		Matcher matcher = p.matcher(msg);
		while (matcher.find()) {
			styles.add(new StyleRange(h0 + matcher.start(), matcher.end()
					- matcher.start(), null, null, SWT.ITALIC));
		}

		if (!currentDiffs.isEmpty())
			buildDiffs(d, styles, monitor, trace);

		if (trace)
			GitTraceLocation.getTrace().traceExit(
					GitTraceLocation.HISTORYVIEW.getLocation());
	}
