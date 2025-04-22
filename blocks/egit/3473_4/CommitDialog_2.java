	protected void addMessageDropDown(Composite parent) {
		final ToolBar dropDownBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		final ToolItem dropDownItem = new ToolItem(dropDownBar, SWT.PUSH);
		dropDownItem
				.setImage(WorkbenchImages
						.getImage(IWorkbenchGraphicConstants.IMG_LCL_RENDERED_VIEW_MENU));
		dropDownItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Menu menu = new Menu(dropDownBar);
				MenuItem preferencesItem = new MenuItem(menu, SWT.PUSH);
				preferencesItem.setText(UIText.CommitDialog_ConfigureLink);
				preferencesItem.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e1) {
						PreferenceDialog dialog = PreferencesUtil
								.createPreferenceDialogOn(
										getShell(),
										UIPreferences.PAGE_COMMIT_PREFERENCES,
										new String[] { UIPreferences.PAGE_COMMIT_PREFERENCES },
										null);
						if (Window.OK == dialog.open())
							commitText.reconfigure();
					}

				});

				Rectangle b = dropDownItem.getBounds();
				Point p = dropDownItem.getParent().toDisplay(
						new Point(b.x, b.y + b.height));
				menu.setLocation(p.x, p.y);
				menu.setVisible(true);
			}

		});
	}

