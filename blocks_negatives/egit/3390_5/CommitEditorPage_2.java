	private void createDescriptionArea(Composite parent, FormToolkit toolkit) {
		Section description = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
						| ExpandableComposite.EXPANDED);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(description);
		description.setText(UIText.CommitEditorPage_SectionMessage);
