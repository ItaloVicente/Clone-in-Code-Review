		add.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			BrowserDescriptorDialog dialog = new BrowserDescriptorDialog(getShell());
			if (dialog.open() == Window.CANCEL)
				return;
			tableViewer.refresh();
			if (checkedBrowser != null)
				tableViewer.setChecked(checkedBrowser, true);
		}));
