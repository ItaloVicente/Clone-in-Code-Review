		final ToolBar dropDownBar = new ToolBar(main, SWT.FLAT | SWT.RIGHT);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).applyTo(dropDownBar);
		final ToolItem dropDownItem = new ToolItem(dropDownBar, SWT.DROP_DOWN);
		Image dropDownImage = UIIcons.HIERARCHY.createImage();
		UIUtils.hookDisposal(dropDownItem, dropDownImage);
		dropDownItem.setImage(dropDownImage);
		final Menu menu = new Menu(dropDownBar);
		dropDownItem.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				menu.dispose();
			}
		});
		dropDownItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Rectangle b = dropDownItem.getBounds();
				Point p = dropDownItem.getParent().toDisplay(
						new Point(b.x, b.y + b.height));
				menu.setLocation(p.x, p.y);
				menu.setVisible(true);
			}

		});

		final MenuItem showRepoRelative = new MenuItem(menu, SWT.RADIO);
