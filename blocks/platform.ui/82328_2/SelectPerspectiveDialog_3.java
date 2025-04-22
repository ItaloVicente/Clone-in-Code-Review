
	private void handleTableViewerKeyPressed(KeyEvent event) {
		if (event.keyCode == SWT.F2 && event.stateMask == 0) {
			IStructuredSelection selection = (IStructuredSelection) list.getSelection();
			if (selection.size() == 1) {
				Object o = selection.getFirstElement();
				if (o instanceof IPerspectiveDescriptor) {
					String description = ((IPerspectiveDescriptor) o).getDescription();
					if (description.length() == 0)
						description = WorkbenchMessages.SelectPerspective_noDesc;
					popUp(description);
				}
			}
		}
	}

	private void popUp(final String description) {
		new PopupDialog(getShell(), PopupDialog.HOVER_SHELLSTYLE, true, false, false, false, false, null, null) {
			private static final int CURSOR_SIZE = 15;

			@Override
			protected Point getInitialLocation(Point initialSize) {
				Display display = getShell().getDisplay();
				Point location = display.getCursorLocation();
				location.x += CURSOR_SIZE;
				location.y += CURSOR_SIZE;
				return location;
			}

			@Override
			protected Control createDialogArea(Composite parent) {
				Label label = new Label(parent, SWT.WRAP);
				label.setText(description);
				label.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent event) {
						close();
					}
				});
				GridData gd = new GridData(GridData.BEGINNING | GridData.FILL_BOTH);
				gd.horizontalIndent = PopupDialog.POPUP_HORIZONTALSPACING;
				gd.verticalIndent = PopupDialog.POPUP_VERTICALSPACING;
				label.setLayoutData(gd);
				return label;
			}
		}.open();
	}
