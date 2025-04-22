		Config config = repository.getConfig();

		commitMessageSection = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(commitMessageSection);
		GridLayoutFactory.fillDefaults().numColumns(1)
				.applyTo(commitMessageSection);

		Composite titleBar = new Composite(commitMessageSection, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(titleBar);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(titleBar);
		Label areaTitle = new Label(titleBar, SWT.NONE);
		areaTitle.setText(UIText.StagingView_CommitMessage);
		areaTitle.setFont(JFaceResources.getFontRegistry()
				.getBold(JFaceResources.DEFAULT_FONT));
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(areaTitle);

		Composite commitMessageToolbarComposite = new Composite(titleBar,
				SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.END, SWT.CENTER)
				.applyTo(commitMessageToolbarComposite);
		RowLayout layout = new RowLayout();
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		commitMessageToolbarComposite.setLayout(layout);
		ToolBarManager commitMessageToolBarManager = new ToolBarManager(
				SWT.FLAT | SWT.HORIZONTAL);

		IAction previewAction = new Action(
				UIText.StagingView_Preview_Commit_Message,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				if (isChecked()) {
					previewLayout.topControl = previewArea;
					areaTitle.setText(UIText.StagingView_CommitMessagePreview);
					previewer.setText(repository,
							messageArea.getCommitMessage());
				} else {
					previewLayout.topControl = messageArea;
					areaTitle.setText(UIText.StagingView_CommitMessage);
				}
				areaTitle.requestLayout();
				previewLayout.topControl.getParent().layout(true, true);
				commitMessageSection.redraw();
				if (!isChecked()) {
					messageArea.setFocus();
				}
			}
		};
		previewAction.setImageDescriptor(UIIcons.ELCL16_PREVIEW);
		commitMessageToolBarManager.add(previewAction);
		commitMessageToolBarManager.add(new Separator());

		IAction signOffAction = new Action(UIText.StagingView_Add_Signed_Off_By,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				commitComponent.setSignedOffButtonSelection(isChecked());
			}
		};
		signOffAction.setImageDescriptor(UIIcons.SIGNED_OFF);
		commitMessageToolBarManager.add(signOffAction);

		addChangeIdAction = new Action(UIText.StagingView_Add_Change_ID,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				commitComponent.setChangeIdButtonSelection(isChecked());
			}
		};
		addChangeIdAction.setImageDescriptor(UIIcons.GERRIT);
		boolean hasChangeId = hasChangeIdFooter(commitMessage);
		addChangeIdAction.setChecked(hasChangeId);
		addChangeIdAction.setEnabled(
				hasChangeId || GerritUtil.getCreateChangeId(config));
		commitMessageToolBarManager.add(addChangeIdAction);

		ToolBar tb = commitMessageToolBarManager
				.createControl(commitMessageToolbarComposite);
		tb.setBackground(null);

		Composite commitMessageTextComposite = new Composite(
				commitMessageSection, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(commitMessageTextComposite);

		previewLayout = new StackLayout();
		commitMessageTextComposite.setLayout(previewLayout);

		messageArea = new SpellcheckableMessageArea(commitMessageTextComposite,
				"", SWT.NONE); //$NON-NLS-1$
		messageArea.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
