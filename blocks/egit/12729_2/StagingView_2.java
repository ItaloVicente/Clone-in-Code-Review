
		ControlContribution controlContribution = new ControlContribution(
				"StagingView.searchText") { //$NON-NLS-1$
			@Override
			protected Control createControl(Composite parent) {
				Composite toolbarComposite = toolkit.createComposite(parent,
						SWT.NONE);
				toolbarComposite.setBackground(null);
				GridLayout headLayout = new GridLayout();
				headLayout.numColumns = 2;
				headLayout.marginHeight = 0;
				headLayout.marginWidth = 0;
				headLayout.marginTop = 0;
				headLayout.marginBottom = 0;
				headLayout.marginLeft = 0;
				headLayout.marginRight = 0;
				toolbarComposite.setLayout(headLayout);

				searchText = new Text(toolbarComposite, SWT.SEARCH
						| SWT.ICON_CANCEL | SWT.ICON_SEARCH);
				searchText.setMessage(UIText.StagingView_Find);
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.widthHint = 150;
				searchText.setLayoutData(data);
				final Display display = Display.getCurrent();
				searchText.addModifyListener(new ModifyListener() {
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
				return toolbarComposite;
			}
		};

