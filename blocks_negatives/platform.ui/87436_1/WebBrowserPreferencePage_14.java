		edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = ((IStructuredSelection) tableViewer
						.getSelection());
				IBrowserDescriptor browser2 = (IBrowserDescriptor) sel
						.getFirstElement();
				IBrowserDescriptorWorkingCopy wc = browser2.getWorkingCopy();
				BrowserDescriptorDialog dialog = new BrowserDescriptorDialog(
						getShell(), wc);
				if (dialog.open() != Window.CANCEL) {
					try {
						tableViewer.refresh(wc.save());
					} catch (Exception ex) {
					}
