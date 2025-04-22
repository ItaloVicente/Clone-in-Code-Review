		search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final List<IBrowserDescriptorWorkingCopy> foundBrowsers = new ArrayList<>();
				final List<String> existingPaths = WebBrowserUtil
						.getExternalBrowserPaths();

				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setMessage(Messages.selectDirectory);
				dialog.setText(Messages.directoryDialogTitle);

				String path = dialog.open();
				if (path == null)
					return;
