				.align(SWT.FILL, SWT.FILL).applyTo(messageArea);

		previewArea = new Composite(commitMessageTextComposite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(previewArea);
		previewArea.setLayout(new FillLayout());
		previewArea.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		previewer = new CommitMessagePreviewer();
		previewer.createControl(previewArea);

		previewLayout.topControl = messageArea;
