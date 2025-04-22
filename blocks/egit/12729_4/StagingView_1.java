		Composite formToolbarComposite = toolkit
				.createComposite(form.getHead());
		formToolbarComposite.setBackground(null);
		RowLayout formRowLayout = new RowLayout();
		formRowLayout.marginHeight = 0;
		formRowLayout.marginWidth = 0;
		formRowLayout.marginTop = 0;
		formRowLayout.marginBottom = 0;
		formRowLayout.marginLeft = 0;
		formRowLayout.marginRight = 0;
		formToolbarComposite.setLayout(formRowLayout);

		form.setHeadClient(formToolbarComposite);
		filterText = new Text(formToolbarComposite, SWT.SEARCH
				| SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		filterText.setMessage(UIText.StagingView_Find);
		RowData data = new RowData();
		data.width = 250;
		filterText.setLayoutData(data);
		final Display display = Display.getCurrent();
		filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				final StagingViewSearchThread searchThread = new StagingViewSearchThread(
						StagingView.this);
				display.timerExec(200, new Runnable() {
					public void run() {
						searchThread.start();
					}
				});
			}
		});

