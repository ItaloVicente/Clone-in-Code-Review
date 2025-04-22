		authorText.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults().indent(5, 0)
				.grab(true, false).align(SWT.FILL, SWT.CENTER).create());

		createPersonLabel(composite, UIIcons.ELCL16_COMMITTER,
				UIText.StagingView_Committer);
