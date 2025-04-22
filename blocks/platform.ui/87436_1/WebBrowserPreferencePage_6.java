		edit.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			IStructuredSelection sel = ((IStructuredSelection) tableViewer.getSelection());
			IBrowserDescriptor browser2 = (IBrowserDescriptor) sel.getFirstElement();
			IBrowserDescriptorWorkingCopy wc = browser2.getWorkingCopy();
			BrowserDescriptorDialog dialog = new BrowserDescriptorDialog(getShell(), wc);
			if (dialog.open() != Window.CANCEL) {
				try {
					tableViewer.refresh(wc.save());
				} catch (Exception ex) {
