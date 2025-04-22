	private List<String> getTags() {
		RevCommit commit = getCommit().getRevCommit();
		Repository repository = getCommit().getRepository();
		List<String> tags = new ArrayList<String>();
		for (Ref tag : repository.getTags().values())
			if (commit.equals(repository.peel(tag).getPeeledObjectId()))
				tags.add(Repository.shortenRefName(tag.getName()));
		Collections.sort(tags);
		return tags;
	}

	private void createTagsArea(Composite parent, FormToolkit toolkit, int span) {
		Composite tagArea = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(tagArea);
		GridDataFactory.fillDefaults().span(span, 1).grab(true, false)
				.applyTo(tagArea);
		toolkit.createLabel(tagArea, UIText.CommitEditorPage_LabelTags)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		fillTags(tagArea, toolkit);
	}

	private void fillTags(Composite parent, FormToolkit toolkit) {
		if (tagLabelArea != null)
			tagLabelArea.dispose();
		tagLabelArea = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().spacing(1, 1).numColumns(4)
				.applyTo(tagLabelArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tagLabelArea);
		List<String> tags = getTags();
		for (String tag : tags) {
			CLabel tagLabel = new CLabel(tagLabelArea, SWT.NONE);
			toolkit.adapt(tagLabel, false, false);
			tagLabel.setImage(getImage(UIIcons.TAG));
			tagLabel.setText(tag);
		}
	}

	private void createMessageArea(Composite parent, FormToolkit toolkit,
			int span) {
		Section messageSection = createSection(parent, toolkit, span);
		Composite messageArea = createSectionClient(messageSection, toolkit);

		messageSection.setText(UIText.CommitEditorPage_SectionMessage);

		RevCommit commit = getCommit().getRevCommit();
		String message = commit.getFullMessage();

		PersonIdent author = commit.getAuthorIdent();
		if (author != null)
			message = replaceSignedOffByLine(message, author);
		PersonIdent committer = commit.getCommitterIdent();
		if (committer != null)
			message = replaceSignedOffByLine(message, committer);
