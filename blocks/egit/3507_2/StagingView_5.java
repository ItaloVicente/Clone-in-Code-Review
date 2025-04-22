		messageSection = new CommitMesageSection();
		messageSection.createControl(horizontalSashForm, toolkit);

		Section stagedSection = toolkit.createSection(veriticalSashForm,
				ExpandableComposite.TITLE_BAR);
		stagedSection.setText(UIText.StagingView_StagedChanges);
		Composite stagedTableComposite = toolkit.createComposite(stagedSection);
		toolkit.paintBordersFor(stagedTableComposite);
		stagedSection.setClient(stagedTableComposite);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(stagedTableComposite);

		stagedTableViewer = new TableViewer(toolkit.createTable(
				stagedTableComposite, SWT.FULL_SELECTION | SWT.MULTI));
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(stagedTableViewer.getControl());
		stagedTableViewer.getTable().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
