			public void run() {
			}
		};
		form.getToolBarManager().add(commitAction);
		form.getToolBarManager().update(true);
		Image repoImage = UIIcons.REPOSITORY.createImage();
		UIUtils.hookDisposal(form, repoImage);
		form.setImage(repoImage);
		form.setText(""); //$NON-NLS-1$
		GridDataFactory.fillDefaults().grab(true, true).applyTo(form);
		toolkit.decorateFormHeading(form.getForm());
		GridLayoutFactory.swtDefaults().applyTo(form.getBody());

		SashForm horizontalSashForm = new SashForm(form.getBody(), SWT.NONE);
		toolkit.adapt(horizontalSashForm, true, true);
		horizontalSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		SashForm veriticalSashForm = new SashForm(horizontalSashForm,
				SWT.VERTICAL);
		toolkit.adapt(veriticalSashForm, true, true);
		veriticalSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		Section unstagedSection = toolkit.createSection(veriticalSashForm,
				ExpandableComposite.TITLE_BAR);
		unstagedSection.setText(UIText.StagingView_UnstagedChanges);

		Composite unstagedTableComposite = toolkit
				.createComposite(unstagedSection);
		toolkit.paintBordersFor(unstagedTableComposite);
		unstagedSection.setClient(unstagedTableComposite);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(unstagedTableComposite);

		unstagedTableViewer = new TableViewer(toolkit.createTable(
				unstagedTableComposite, SWT.FULL_SELECTION | SWT.MULTI));
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(unstagedTableViewer.getControl());
		unstagedTableViewer.getTable().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
