		Composite commitMessageComposite = toolkit
				.createComposite(commitMessageSection);
		toolkit.paintBordersFor(commitMessageComposite);
		commitMessageSection.setClient(commitMessageComposite);
		GridLayoutFactory.fillDefaults().numColumns(1)
				.extendedMargins(2, 2, 2, 2).applyTo(commitMessageComposite);
