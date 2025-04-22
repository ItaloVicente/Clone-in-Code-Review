	private Image getImage(ImageDescriptor descriptor) {
		return (Image) this.resources.get(descriptor);
	}

	private Section createSection(Composite parent, FormToolkit toolkit,
			int span) {
		Section section = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
						| ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().span(span, 1).grab(true, true)
				.applyTo(section);
		return section;
	}

	private Composite createSectionClient(Section parent, FormToolkit toolkit) {
		Composite client = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(client);
		return client;
	}

	private boolean isSignedOffBy(PersonIdent person) {
		RevCommit commit = getCommit().getRevCommit();
		return commit.getFullMessage().indexOf(getSignedOffByLine(person)) != -1;
	}

	private String getSignedOffByLine(PersonIdent person) {
		return MessageFormat.format(SIGNED_OFF_BY, person.getName(),
				person.getEmailAddress());
	}

	private String replaceSignedOffByLine(String message, PersonIdent person) {
		Pattern pattern = Pattern.compile(
				"^\\s*" + Pattern.quote(getSignedOffByLine(person)) //$NON-NLS-1$
						+ "\\s*$", Pattern.MULTILINE); //$NON-NLS-1$
		return pattern.matcher(message).replaceAll(""); //$NON-NLS-1$
	}

	private Composite createUserArea(Composite parent, FormToolkit toolkit,
			PersonIdent person, boolean author) {
		Composite userArea = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().spacing(2, 2).numColumns(3)
				.applyTo(userArea);

		Label userLabel = toolkit.createLabel(userArea, null);
		userLabel.setImage(getImage(author ? UIIcons.ELCL16_AUTHOR
				: UIIcons.ELCL16_COMMITTER));
		if (author)
			userLabel.setToolTipText(UIText.CommitEditorPage_TooltipAuthor);
		else
			userLabel.setToolTipText(UIText.CommitEditorPage_TooltipCommitter);

		boolean signedOff = isSignedOffBy(person);

		Text userText = toolkit
				.createText(userArea, MessageFormat.format(
						author ? UIText.CommitEditorPage_LabelAuthor
								: UIText.CommitEditorPage_LabelCommitter,
						person.getName(), person.getEmailAddress(), person
								.getWhen()));

		GridDataFactory.fillDefaults().span(signedOff ? 1 : 2, 1)
				.applyTo(userText);
		if (signedOff) {
			Label signedOffLabel = toolkit.createLabel(userArea, null);
			signedOffLabel.setImage(getImage(UIIcons.SIGNED_OFF));
			if (author)
				signedOffLabel
						.setToolTipText(UIText.CommitEditorPage_TooltipSignedOffByAuthor);
			else
				signedOffLabel
						.setToolTipText(UIText.CommitEditorPage_TooltipSignedOffByCommitter);
		}

		return userArea;
	}

	private void updateSectionClient(Section section, Composite client,
			FormToolkit toolkit) {
		hookExpansionGrabbing(section);
		toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	private void createHeaderArea(Composite parent, FormToolkit toolkit,
			int span) {
