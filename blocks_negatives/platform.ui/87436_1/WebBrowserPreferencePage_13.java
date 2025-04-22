		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BrowserDescriptorDialog dialog = new BrowserDescriptorDialog(
						getShell());
				if (dialog.open() == Window.CANCEL)
					return;
				tableViewer.refresh();
				if (checkedBrowser != null)
					tableViewer.setChecked(checkedBrowser, true);
			}
		});
