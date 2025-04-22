		commitMessagePreview = new Composite(commitMessageTextComposite,
				SWT.NONE);
		commitMessagePreview.setLayout(new FillLayout());
		commitMessagePreview.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		previewer = new CommitMessagePreviewer();
		previewer.createControl(commitMessagePreview);

		previewLayout.topControl = commitMessageText;

