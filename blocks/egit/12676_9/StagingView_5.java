
		createStagedToolBarComposite();

		Composite stagedComposite = toolkit.createComposite(stagedSection);
		toolkit.paintBordersFor(stagedComposite);
		stagedSection.setClient(stagedComposite);
